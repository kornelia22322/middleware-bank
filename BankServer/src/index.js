const grpc = require('grpc');
const readline = require('readline');
const thrift = require('thrift');
import { Processor as AccountManagement } from '../gen-nodejs/AccountManagement';
import { Processor as AccountService } from '../gen-nodejs/AccountService';
import { Processor as PremiumAccountService } from '../gen-nodejs/PremiumAccountService';
import {
  Data,
  LoanCosts,
  AccountInfo,
  AuthorizationException,
  LoanConfig,
} from '../gen-nodejs/bank_types';

// let port = 9090;
const premiumAccountThreshold = 2000;
let currentGuid = 0;
const accounts = {};
const clients = {};
let port;
const commision = 0.05;
const credit_rate = 0.08;

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

const PROTO_PATH = __dirname + '/../../protos/currencies.proto';

const currencies_proto = grpc.load(PROTO_PATH).currencies;
const currencyRates = {};
const currenciesSupported = [];

function initCurrencyService() {
  const currencies = new currencies_proto.Currencies('localhost:50051', grpc.credentials.createInsecure());
  const call = currencies.currencyRates();

  //send request
  currenciesSupported.forEach((currency) => {
    call.write(currency);
  })

  call.on('data', (data) => {
      console.log("Received " + data.currencyName + " with rate " + data.currencyRate);
      currencyRates[data.currencyName] = data.currencyRate;
    }
  );
}

function create (data) {
  console.log("I am here...");
  console.log('Request for account creating with data:\n', data);
  const account = new Data(data);

  const isPremium = data.income > premiumAccountThreshold ? true : false;
  const account_guid = String(currentGuid);
  currentGuid++;
  clients[data.pesel] = data;

  accounts[account_guid] = new AccountInfo({
    balance: 0,
    guid: account_guid,
    isPremium,
  });
  console.log('Created account', accounts[account_guid]);
  return accounts[account_guid];
}

function getLoanInfo(guid, loanConfig) {
  console.log('Received request for loanDetails with parameters: \n', guid, loanConfig);
  const parameters = new LoanConfig(loanConfig);

  if (!accounts[guid]) {
    console.log('Account does not exist');
    throw new AuthorizationException({ why: 'Wrong guid' });
  } else {
    if (!accounts[guid].isPremium) {
      console.log('Expected premium account, received standard account');
      throw new AuthorizationException({ why: 'Account is not a premium!' })
    }
    console.log('Calculating loanCosts');
    const account = accounts[guid];
    const loanCosts = new LoanCosts();
    const commisionCosts = loanConfig.moneyAmount * commision;
    const timeCost = loanConfig.moneyAmount + currencyRates[loanConfig.currency] * loanConfig.moneyAmount * credit_rate * loanConfig.daysCount/365;
    const total = timeCost + commisionCosts;
    loanCosts.currencyCost = total;
    console.log('LoanCosts calculated: ', loanCosts);

    accounts[guid].balance += total;
    return loanCosts;
  }
}

function getAccountInfo(guid) {
  console.log('Request for accountDetails with GUID: \n', guid);
  if (!accounts[guid]) {
    console.log('Account does not exist');
    throw new AuthorizationException({ why: 'Wrong guid' });
  } else {
    return accounts[guid];
  }
}

const accountManagementHandler = new AccountManagement({
  create,
});

const premiumAccountServiceHandler = new PremiumAccountService({
  getLoanInfo,
  getAccountInfo,
});

const accountServiceHandler = new AccountService({
  getAccountInfo,
});


async function startBankService() {
  const processor = new thrift.MultiplexedProcessor();
  processor.registerProcessor('AccountManagement', accountManagementHandler);
  processor.registerProcessor('AccountService', accountServiceHandler);
  processor.registerProcessor('PremiumAccountService', premiumAccountServiceHandler);
  const server = thrift.createMultiplexServer(processor);
  console.log(`Starting server on ${port}`);
  server.listen(port);
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

initCurrencyService();
startBankService();
