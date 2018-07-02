const grpc = require('grpc');

const PROTO_PATH = __dirname + '/../../protos/currencies.proto';

const PORT = 50051;

const currencies_proto = grpc.load(PROTO_PATH).currencies;

const supportedCurrencies = {'USD' : 1, 'EUR' : 1, 'CHF' : 1};
const subscribersforCurrency = {};

let id = 0;

function calculateCurrenciesRates() {
  Object.entries(supportedCurrencies).forEach(([currency, rate]) => {
      supportedCurrencies[currency] = Math.random()*3 + 2;
      notify(currency);
  });
}

function simulateCurrenciesRates(interval, func) {
  setInterval(func, interval);
}

function notify(currency) {
  console.log(`Notifying subscribers about ${currency} rate changed to ${supportedCurrencies[currency]}`);
  subscribersforCurrency[currency].forEach(subscriber => {
    try {
      subscriber.write({currencyRate: supportedCurrencies[currency], currencyName: currency})
    } catch(_) {
      console.log('Error while sending message to bank. Removing it from subscribers list');
      removeSubscriber(subscriber);
    }
  })
}

function removeSubscriber(subscriber) {
  Object.keys(subscribersforCurrency).forEach((key) => {
      subscribersforCurrency[key] = subscribersforCurrency[key].filter(sub => sub !== subscriber);
    }
  );
}

function getEntries() {
  return Object
    .entries(supportedCurrencies)
    .map(([currencyName, currencyRate]) => ({ currencyRate, currencyName }));
}

function currencyRates(call) {
  const bankId = id++;

  console.log(`Bank ${bankId} connected`);

  Object.keys(supportedCurrencies).forEach(currency => {
    call.write({currencyName : currency, currencyRate: supportedCurrencies[currency]});
  })

  call.on('data', (msg) => {
    if (msg.currencyName != undefined && supportedCurrencies[msg.currencyName] != undefined) {
      if (subscribersforCurrency[msg.currencyName].indexOf(call) === -1){
        console.log(`Bank ${bankId} subscribed ${msg.currencyName}`);
        subscribersforCurrency[msg.currencyName].push(call);
      }
    }
  });

  call.on('end', () => {
    console.log(`Bank ${bankId} disconnected`);
    removeSubscriber(call);
  });
}

console.log(`Currency server on starting port ${PORT}`);
const server = new grpc.Server();
/* addService(service, implementation)
Add a service to the server, with a corresponding implementation. */
/* The Node.js library dynamically generates service descriptors
and client stub definitions from .proto files loaded at runtime. */
server.addService(currencies_proto.Currencies.service, { currencyRates });
server.bind(`0.0.0.0:${PORT}`, grpc.ServerCredentials.createInsecure());
server.start();

Object.keys(supportedCurrencies).forEach(curr => {
    subscribersforCurrency[curr] = [];
});


calculateCurrenciesRates();
simulateCurrenciesRates(7000, calculateCurrenciesRates);
