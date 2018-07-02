import bank.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.BufferedInputStream;
import java.util.*;

public class BankClient {
    private Map<Integer, BankClient> banks = new HashMap<>();

    private List<Integer> standartAccounts = new LinkedList<>();
    private List<Integer> premiumAccounts = new LinkedList<>();

    private AccountManagement.Client accountManagement;
    private AccountService.Client accountService;
    private PremiumAccountService.Client premiumAccountService;

    public static void main (String[] args) throws TTransportException {
        BankClient bankClient = new BankClient();
        bankClient.start();
    }

    public void init(int port) throws TTransportException {
        TTransport transport;
        transport = new TSocket("localhost", port);

        TProtocol protocol;
        protocol = new TBinaryProtocol(transport, true, true);

        accountManagement = new AccountManagement
                .Client(new TMultiplexedProtocol(protocol, "AccountManagement"));
        accountService = new AccountService
                .Client(new TMultiplexedProtocol(protocol, "AccountService"));
        premiumAccountService = new PremiumAccountService
                .Client(new TMultiplexedProtocol(protocol, "PremiumAccountService"));

        transport.open();
    }

    public void start() {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int port;
        BankClient bank;

        System.out.print("PORT: ");
        port = Integer.parseInt(scanner.nextLine());

        banks.put(port, this);
        try {
            this.init(port);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        this.clientCommandLine(scanner);
    }

    public void clientCommandLine(Scanner scanner) {
        String command;
        String currency,
                pesel, firstname, surname;
        int guid, numDays;
        float moneyAmount, income;

        System.out.println("Available commands : list_account / get_account / get_loan / create_account");

        while (!(command = scanner.nextLine()).equals("/q")) {
        try {
            switch (command.trim().toLowerCase()) {
                case "list_account":
                    System.out.println("Standard accounts:");
                    standartAccounts.forEach(id -> System.out.println("\t" + id));
                    System.out.println("Premium accounts:");
                    premiumAccounts.forEach(id -> System.out.println("\t" + id));
                    break;
                case "get_account":
                    System.out.print("GUID: ");
                    guid = Integer.parseInt(scanner.nextLine().trim());
                    if (standartAccounts.contains(guid)) {
                        System.out.println(accountService.getAccountInfo(guid));
                    } else if (premiumAccounts.contains(guid)) {
                        System.out.println(premiumAccountService.getAccountInfo(guid));
                    } else {
                        AccountInfo accountInfo = accountService.getAccountInfo(guid);
                        if (accountInfo.isPremium) {
                            premiumAccounts.add(accountInfo.guid);
                        } else {
                            standartAccounts.add(accountInfo.guid);
                        }
                        System.out.println(accountInfo);
                    }

                    break;
                case "get_loan":
                    System.out.print("GUID: ");
                    guid = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Currency: ");
                    currency = scanner.nextLine().trim();
                    System.out.print("Money amount: ");
                    moneyAmount = Float.parseFloat(scanner.nextLine());
                    System.out.print("Number of days: ");
                    numDays = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println(premiumAccountService.getLoanInfo(
                            guid,
                            new LoanConfig(currency, moneyAmount, numDays)
                    ));
                    break;
                case "create_account":
                    System.out.print("PESEL: ");
                    pesel = scanner.nextLine().trim();
                    System.out.print("Income: ");
                    income = Float.parseFloat(scanner.nextLine());
                    System.out.print("Firstname: ");
                    firstname = scanner.nextLine().trim();
                    System.out.print("Lastname: ");
                    surname = scanner.nextLine().trim();
                    AccountInfo accountDetails = accountManagement
                            .create(new Data(
                                    pesel,
                                    firstname,
                                    surname,
                                    income
                            ));

                    if (accountDetails.isPremium) {
                        premiumAccounts.add(accountDetails.guid);
                    } else {
                        standartAccounts.add(accountDetails.guid);
                    }
                    System.out.println(accountDetails);
                    break;
            }
        } catch (AuthorizationException e) {
            System.out.println("InvalidArgumentException: " + e.why);
        } catch (TException e) {
            e.printStackTrace();
        }
        }
    }
}