const grpc = require('grpc');
const readline = require('readline');

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

let port = 9090;

process.argv.forEach((currency, index) => {
    if(index == 2) {
      port = currency;
    } else if(index > 2){
      console.log(currency);
      currenciesSupported.push(currency);
    }
})

init();
