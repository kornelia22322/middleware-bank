import bank.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.BufferedInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankClient {
    private Map<Integer, Bank> banks = new HashMap<>();

    public static void main (String[] args) throws TTransportException {
        BankClient bankClient = new BankClient();
        bankClient.start();
    }

    public void start() {
        printMenu();
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        String command;
        Bank bank;
        int port;

        while (!(command = scanner.nextLine()).equals("/q")) {
            try {
                switch (command.trim().toLowerCase()) {
                    case "openbank":
                        System.out.print("PORT: ");
                        port = Integer.parseInt(scanner.nextLine());
                        bank = banks.getOrDefault(port, new Bank(port));
                        banks.put(port, bank);
                        bank.runApp();
                        break;
                    default:
                        printMenu();
                }
            } catch (TTransportException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMenu() {
        System.out.println("/q - closes");
        System.out.println("openBank - starts opening bank process");
    }
}