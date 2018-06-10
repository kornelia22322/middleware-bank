const grpc = require('grpc');
const readline = require('readline');
const thrift = require('thrift');
import { Processor as AccountManagement } from '../gen-nodejs/AccountManagement';
import { Processor as AccountService } from '../gen-nodejs/AccountService';
import { Processor as PremiumAccountService } from '../gen-nodejs/PremiumAccountService';
import {
  Account,
  LoanCosts,
  AccountDetails,
  AuthorizationException,
  InvalidArgumentException,
  LoanParameters,
} from '../gen-nodejs/bank_types';

let port = 9090;

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

const PROTO_PATH = __dirname + '/../../protos/currencies.proto';

const currencies_proto = grpc.load(PROTO_PATH).currencies;
const currencyRates = {};
const currenciesSupported = [];

function init() {
  const currencies = new currencies_proto.Currencies('localhost:50051', grpc.credentials.createInsecure());
  const call = currencies.currencyRates();

  currenciesSupported.forEach((currency) => {
    call.write(currency);
  })

  call.on('data', (data) => {
    const msg = data[data.msg];
    if (msg.entries) {
      msg.entries.forEach(entry => {
        console.log(entry);
        currencyRates[entry.currencyName] = entry.currencyRate;
      });
    } else {
      console.log(msg);
      currencyRates[msg.currencyName] = msg.currencyRate;
    }
  });
}

const PESEL_checker = new RegExp('[0-9]{11}');

const settings = {};
const defaultSettings = {
  PREMIUM_THRESHOLD: 10000,
  COMMISION: 0.001,
  CREDIT_RATE: 0.05,
};

let guid = 0;

const accounts = {};
const clients = {};


function authenticate(guid) {
  if (!guid && guid !== '0') {
    console.log('Missing GUID parameter');
    throw new InvalidArgumentException({ why: 'Missing guid' })
  }

  if (!accounts[guid]) {
    console.log('Account does not exist');
    throw new AuthorizationException({ why: 'Authorization failure, wrong guid' });
  }
}

function createAccount(account) {
  console.log('Received request for createAccount with parameters:\n', account);
  const accountObject = new Account(account);

  if (!PESEL_checker.test(accountObject.pesel)) {
    console.log('Wrong PESEL format');
    throw new InvalidArgumentException({why: 'Wrong PESEL format'});
  }

  if (!accountObject.firstname) {
    console.log('Missing firstname');
    throw new InvalidArgumentException({why: 'Missing firstname'});
  }

  if (!accountObject.lastname) {
    console.log('Missing lastname');
    throw new InvalidArgumentException({why: 'Missing lastname'});
  }

  if (!accountObject.incomeThreshold || accountObject.incomeThreshold < 0) {
    console.log('Invalid incomeThreshold value');
    throw new InvalidArgumentException({why: 'Invalid incomeThreshold value'});
  }

  if (!accountObject.nativeCurrency) {
    console.log('Missing nativeCurrency');
    throw new InvalidArgumentException({why: 'Missing nativeCurrency'});
  }

  if (currenciesSupported.indexOf(accountObject.nativeCurrency.toUpperCase()) === -1) {
    console.log('Currency not supported');
    throw new InvalidArgumentException({why: `Currency not supported, supported currencies: ${currenciesSupported}`});
  }

  const premiumThreshold = settings.PREMIUM_THRESHOLD || defaultSettings.PREMIUM_THRESHOLD;
  const isPremium =
    accountObject.incomeThreshold *
    currencyRates[accountObject.nativeCurrency.toUpperCase()] > premiumThreshold;
  const account_guid = String(guid++);

  clients[accountObject.pesel] = accountObject;

  accounts[account_guid] = new AccountDetails({
    balance: 0,
    currency: accountObject.nativeCurrency.toUpperCase(),
    isPremium,
    guid: account_guid,
  });
  console.log('Created account', accounts[account_guid]);
  return accounts[account_guid];
}

function getLoanDetails(guid, loanParameters) {
  console.log('Received request for loanDetails with parameters: \n', guid, loanParameters);
  const parameters = new LoanParameters(loanParameters);

  authenticate(guid);

  if (!accounts[guid].isPremium) {
    console.log('Expected premium account, received standard account');
    throw new AuthorizationException({ why: 'Account is not a premium account' })
  }

  if (!parameters.startDate) {
    console.log('Missing start Date');
    throw new InvalidArgumentException({ why: 'Missing startDate' })
  }

  if (!parameters.closeDate) {
    console.log('Missing close Date');
    throw new InvalidArgumentException({ why: 'Missing closeDate' })
  }

  if (!parameters.currency) {
    console.log('Missing currency');
    throw new InvalidArgumentException({ why: 'Missing currency' })
  }

  if (!parameters.moneyAmount || parameters.moneyAmount < 0) {
    console.log('Missing moneyAmount or invalid value');
    throw new InvalidArgumentException({ why: 'Missing moneyAmount or invalid value' })
  }

  if (!moment(parameters.startDate).isValid()) {
    console.log('Unparsable startDate format');
    throw new InvalidArgumentException({ why: 'Invalid startDate format' })
  }

  if (!moment(parameters.closeDate).isValid()) {
    console.log('Unparsable endDate format');
    throw new InvalidArgumentException({ why: 'Invalid closeDate format' })
  }

  if (moment(parameters.closeDate).isBefore(parameters.startDate)) {
    console.log('Invalid closeDate value');
    throw new InvalidArgumentException({ why: 'Close date should be after the startDate' })
  }

  if (moment(parameters.startDate).isBefore(moment())) {
    console.log('Invalid startDate value');
    throw new InvalidArgumentException({ why: 'startDate should be in future' })
  }

  if (currenciesSupported.indexOf(parameters.currency.toUpperCase()) === -1) {
    console.log('Unsupported currency');
    throw new InvalidArgumentException({ why: `Unsupported currency, currencies supported ${currenciesSupported}` })
  }

  console.log('Calculating loanCosts');
  const account = accounts[guid];
  const loanCosts = new LoanCosts();
  const commisionCosts = parameters.moneyAmount * (settings.COMMISION || defaultSettings.COMMISION);
  const duration = Math.round(moment.duration(moment(parameters.closeDate) - moment(parameters.startDate)).asDays());
  const rates = parameters.moneyAmount * (settings.CREDIT_RATE || defaultSettings.CREDIT_RATE) * duration / 365;

  loanCosts.requestedCurrencyCost = parameters.moneyAmount + rates + commisionCosts;
  loanCosts.nativeCurrencyCost = loanCosts.requestedCurrencyCost /
    currencyRates[account.currency] * currencyRates[parameters.currency.toUpperCase()];

  console.log('LoanCosts calculated: ', loanCosts);

  return loanCosts;
}

function getAccountDetails(guid) {
  console.log('Received request for accountDetails with GUID: \n', guid);
  authenticate(guid);

  return accounts[guid];
}

const accountManagement = new AccountManagement({
  createAccount,
});

const premiumAccountService = new PremiumAccountService({
  getLoanDetails,
  getAccountDetails,
});

const accountService = new AccountService({
  getAccountDetails,
});


async function startBankService() {
  const multiplexedProcessor = new thrift.MultiplexedProcessor();
  multiplexedProcessor.registerProcessor('AccountManagement', accountManagement);
  multiplexedProcessor.registerProcessor('AccountService', accountService);
  multiplexedProcessor.registerProcessor('PremiumAccountService', premiumAccountService);
  const server = thrift.createMultiplexServer(multiplexedProcessor);
  console.log(`Starting server on port ${port}`);
  server.listen(port);
  console.log('Bank service started');
}

process.argv.forEach((currency, index) => {
    if(index == 2) {
      port = currency;
      console.log(port);
    } else if(index > 2){
      console.log(currency);
      currenciesSupported.push(currency);
    }
})

init();
startBankService();
