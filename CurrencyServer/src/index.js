const grpc = require('grpc');

const PROTO_PATH = __dirname + '/../../protos/currencies.proto';

const PORT = 50001;

const currencies_proto = grpc.load(PROTO_PATH).currencies;

const supportedCurrencies = {'USD' : 1, 'EUR' : 1, 'CHF' : 1};
const subscribersforCurrency = {};

let bankId;

function calculateCurrenciesRates() {
  Object.entries(currencies).forEach(([currency, rate]) => {
      currencies[currency] = Math.random() + 3;
      notifySubscribers(currency);
  });
}

function simulateCurrenciesRates(interval, func) {
  setInterval(func, interval);
}

function notifySubscribers(currency) {
  console.log(`Notifying subscribers about ${currency} rate changed`);
  subscribers[currency].forEach(subscriber => {
    try {
      subscriber.write({currencyRate: {currencyRate: currencies[currency], currencyName: currency}})
    } catch(_) {
      console.log('Error while sending message to bank. Removing it from subscribers');
      removeSubscriber(subscriber);
    }
  })
}

function getEntries() {
  return Object
    .entries(currencies)
    .map(([currencyName, currencyRate]) => ({ currencyRate, currencyName }));
}

function currencyRates(call) {
  const bankId = id++;

  console.log(`Bank ${bankId} connected`);

  call.write({ currenciesRates: { entries: getEntries() } });

  call.on('data', (msg) => {
    if (msg.currencyName && currencies[msg.currencyName]) {
      if (subscribers[msg.currencyName].indexOf(call) === -1){
        console.log(`Bank ${bankId} subscribed ${msg.currencyName}`);
        subscribers[msg.currencyName].push(call);
      }
    } else {
      call.write({ currenciesRates: { entries: getEntries() } })
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
server.addService(currencies_proto.Currencies.service, { currencyRates });
server.bind(`0.0.0.0:${PORT}`, grpc.ServerCredentials.createInsecure());
server.start();
calculateCurrenciesRates();
simulateCurrenciesRates(3000, calculateCurrenciesRates);
