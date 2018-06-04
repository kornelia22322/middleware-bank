const grpc = require('grpc');

const PROTO_PATH = __dirname + '/../../protos/currencies.proto';

const PORT = 50001;

const currencies_proto = grpc.load(PROTO_PATH).currencies;

const supportedCurrencies = ['USD', 'EUR', 'PLN'];
const subscribersforCurrency = [];

console.log(`Currency server on starting port ${PORT}`);

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

const server = new grpc.Server();
/* addService(service, implementation)
Add a service to the server, with a corresponding implementation. */
server.addService(currencies_proto.Currencies.service, { currencyRates });
server.bind(`0.0.0.0:${PORT}`, grpc.ServerCredentials.createInsecure());
server.start();
