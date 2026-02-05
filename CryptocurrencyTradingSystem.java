 /*
 Crypto currency Trading System :
Simulate a crypto trading system where users can buy and sell coins. Implement:
 protect user wallet details
 Store transaction logs
 Check for invalid trades 
 */
 
 

import java.io.*;
import java.util.*;

class User {
    String username;
    String password;
    double balance;
    double btc;

    User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.btc = 0.0;
    }

    void showWallet() {
        System.out.println("Wallet Balance: $" + balance);
        System.out.println("BTC: " + btc);
    }
}

class CryptoExchange {
    private static final double BTC_PRICE = 60000.0;
    List<User> users = new ArrayList<>();
    User currentUser = null;

    void register(String u, String p) {
        users.add(new User(u, p, 10000.0));
        System.out.println(" Registration Successful! Wallet initialized with $10,000.");
    }

    boolean login(String u, String p) {
        for (User user : users) {
            if (user.username.equals(u) && user.password.equals(p)) {
                currentUser = user;
                System.out.println("Login Successful! Welcome " + u);
                return true;
            }
        }
        System.out.println(" Invalid username or password!");
        return false;
    }

    void buy(double amount) throws IOException {
        double cost = amount * BTC_PRICE;
        if (amount <= 0 || currentUser.balance < cost) {
            System.out.println(" Invalid trade! Not enough balance.");
            return;
        }
        currentUser.balance -= cost;
        currentUser.btc += amount;
        logTransaction("BUY", amount, cost);
        System.out.println("Bought " + amount + " BTC for $" + cost);
    }

    void sell(double amount) throws IOException {
        if (amount <= 0 || currentUser.btc < amount) {
            System.out.println(" Invalid trade! Not enough BTC.");
            return;
        }
        double gain = amount * BTC_PRICE;
        currentUser.btc -= amount;
        currentUser.balance += gain;
        logTransaction("SELL", amount, gain);
        System.out.println("Sold " + amount + " BTC for $" + gain);
    }

    void logTransaction(String type, double amount, double total) throws IOException {
        FileWriter fw = new FileWriter("transactions.txt", true);
        fw.write(currentUser.username + "," + type + "," + amount + "," + total + "\n");
        fw.close();
    }
}

public class CryptocurrencyTradingSystem {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        CryptoExchange exchange = new CryptoExchange();
        int choice;

        while (true) {
            System.out.println("\n=== Crypto Trading Menu ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Buy BTC");
            System.out.println("4. Sell BTC");
            System.out.println("5. Show Wallet");
            System.out.println("6. Exit");
            System.out.print("\nEnter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String u = sc.next();
                    System.out.print("Enter password: ");
                    String p = sc.next();
                    exchange.register(u, p);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    u = sc.next();
                    System.out.print("Enter password: ");
                    p = sc.next();
                    exchange.login(u, p);
                    break;

                case 3:
                    if (exchange.currentUser == null) {
                        System.out.println(" Please login first!");
                        break;
                    }
                    System.out.print("Enter BTC amount to buy: ");
                    double bAmt = sc.nextDouble();
                    exchange.buy(bAmt);
                    break;

                case 4:
                    if (exchange.currentUser == null) {
                        System.out.println(" Please login first!");
                        break;
                    }
                    System.out.print("Enter BTC amount to sell: ");
                    double sAmt = sc.nextDouble();
                    exchange.sell(sAmt);
                    break;

                case 5:
                    if (exchange.currentUser != null)
                        exchange.currentUser.showWallet();
                    else
                        System.out.println(" Please login first!");
                    break;

                case 6:
                    System.out.println(" Exiting... Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
