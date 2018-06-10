import bank.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    private List<String> standartAccountsGuids = new LinkedList<>();
    private List<String> premiumAccountsGuids = new LinkedList<>();

    private AccountManagement.Client accountManagement;
    private AccountService.Client accountService;
    private PremiumAccountService.Client premiumAccountService;

    public Bank(int port) throws TTransportException {
        String host = "localhost";

        TProtocol protocol;
        TTransport transport;

        transport = new TSocket(host, port);
        protocol = new TBinaryProtocol(transport, true, true);

        accountManagement = new AccountManagement
                .Client(new TMultiplexedProtocol(protocol, "AccountManagement"));
        accountService = new AccountService
                .Client(new TMultiplexedProtocol(protocol, "AccountService"));
        premiumAccountService = new PremiumAccountService
                .Client(new TMultiplexedProtocol(protocol, "PremiumAccountService"));

        transport.open();
    }

    public void runApp() {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        String command;
        String guid, currency, startDate, closeDate,
                pesel, firstname, lastname;
        float moneyAmount, incomeThreshold;
        printMenu();

        while (!(command = scanner.nextLine()).equals("/q")) {
            try {
                switch (command.trim().toLowerCase()) {
                    case "listaccounts":
                        System.out.println("Standard accounts:");
                        standartAccountsGuids.forEach(id -> System.out.println("\t" + id));
                        System.out.println("Premium accounts:");
                        premiumAccountsGuids.forEach(id -> System.out.println("\t" + id));
                        break;
                    case "getaccount":
                        System.out.print("GUID: ");
                        guid = scanner.nextLine().trim();
                        if (standartAccountsGuids.contains(guid)) {
                            System.out.println(accountService.getAccountDetails(guid));
                        } else if (premiumAccountsGuids.contains(guid)) {
                            System.out.println(premiumAccountService.getAccountDetails(guid));
                        } else {
                            AccountDetails accountDetails = accountService.getAccountDetails(guid);
                            if (accountDetails.isPremium) {
                                premiumAccountsGuids.add(accountDetails.guid);
                            } else {
                                standartAccountsGuids.add(accountDetails.guid);
                            }
                            System.out.println(accountDetails);
                        }

                        break;
                    case "getloan":
                        System.out.print("GUID: ");
                        guid = scanner.nextLine().trim();
                        System.out.print("Currency: ");
                        currency = scanner.nextLine().trim();
                        System.out.print("Money amount: ");
                        moneyAmount = Float.parseFloat(scanner.nextLine());
                        System.out.print("Start Date: ");
                        startDate = scanner.nextLine().trim();
                        System.out.print("Close Date: ");
                        closeDate = scanner.nextLine().trim();
                        System.out.println(premiumAccountService.getLoanDetails(
                                guid,
                                new LoanParameters(currency, moneyAmount, startDate, closeDate)
                        ));
                        break;
                    case "create":
                        System.out.print("PESEL: ");
                        pesel = scanner.nextLine().trim();
                        System.out.print("Currency: ");
                        currency = scanner.nextLine().trim();
                        System.out.print("Income threshold: ");
                        incomeThreshold = Float.parseFloat(scanner.nextLine());
                        System.out.print("Firstname: ");
                        firstname = scanner.nextLine().trim();
                        System.out.print("Lastname: ");
                        lastname = scanner.nextLine().trim();
                        AccountDetails accountDetails = accountManagement
                                .createAccount(new Account(
                                        pesel,
                                        firstname,
                                        lastname,
                                        incomeThreshold,
                                        currency
                                ));

                        if (accountDetails.isPremium) {
                            premiumAccountsGuids.add(accountDetails.guid);
                        } else {
                            standartAccountsGuids.add(accountDetails.guid);
                        }
                        System.out.println(accountDetails);
                        break;
                    default:
                        printMenu();
                }
            } catch (InvalidArgumentException e) {
                System.out.println("InvalidArgumentException: " + e.why);
            } catch (AuthorizationException e) {
                System.out.println("InvalidArgumentException: " + e.why);
            } catch (TException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMenu() {
        System.out.println("listAccounts - lists accounts");
        System.out.println("getAccount - starts getting account details process");
        System.out.println("getLoan - starts getting loan details process");
        System.out.println("create - starts creating account process");
        System.out.println("/q - closes");
    }
}