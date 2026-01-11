import java.util.*;
import java.io.*;

public class StockTradingPlatform {

    // ----------- STOCK CLASS -----------
    static class Stock implements Serializable {
        String symbol;
        double price;

        Stock(String symbol, double price) {
            this.symbol = symbol;
            this.price = price;
        }
    }

    // ----------- TRANSACTION CLASS -----------
    static class Transaction implements Serializable {
        String type; // BUY or SELL
        String stockSymbol;
        int quantity;
        double price;
        Date date;

        Transaction(String type, String stockSymbol, int quantity, double price) {
            this.type = type;
            this.stockSymbol = stockSymbol;
            this.quantity = quantity;
            this.price = price;
            this.date = new Date();
        }
    }

    // ----------- USER CLASS -----------
    static class User implements Serializable {
        String name;
        double balance;
        Map<String, Integer> portfolio;
        List<Transaction> transactions;

        User(String name, double balance) {
            this.name = name;
            this.balance = balance;
            portfolio = new HashMap<>();
            transactions = new ArrayList<>();
        }

        void buyStock(Stock stock, int qty) {
            double cost = stock.price * qty;
            if (balance >= cost) {
                balance -= cost;
                portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + qty);
                transactions.add(new Transaction("BUY", stock.symbol, qty, stock.price));
                System.out.println("Stock bought successfully!");
            } else {
                System.out.println("Insufficient balance!");
            }
        }

        void sellStock(Stock stock, int qty) {
            int ownedQty = portfolio.getOrDefault(stock.symbol, 0);
            if (ownedQty >= qty) {
                balance += stock.price * qty;
                portfolio.put(stock.symbol, ownedQty - qty);
                transactions.add(new Transaction("SELL", stock.symbol, qty, stock.price));
                System.out.println("Stock sold successfully!");
            } else {
                System.out.println("Not enough stocks to sell!");
            }
        }

        void showPortfolio(Map<String, Stock> market) {
            System.out.println("\n--- Portfolio ---");
            double totalValue = balance;
            System.out.println("Cash Balance: ₹" + balance);

            for (String symbol : portfolio.keySet()) {
                int qty = portfolio.get(symbol);
                double value = market.get(symbol).price * qty;
                totalValue += value;
                System.out.println(symbol + " | Qty: " + qty + " | Value: ₹" + value);
            }

            System.out.println("Total Portfolio Value: ₹" + totalValue);
        }
    }

    // ----------- FILE HANDLING -----------
    static void saveUser(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("portfolio.dat"))) {
            oos.writeObject(user);
        } catch (Exception e) {
            System.out.println("Error saving data");
        }
    }

    static User loadUser() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("portfolio.dat"))) {
            return (User) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    // ----------- MAIN METHOD -----------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Market Stocks
        Map<String, Stock> market = new HashMap<>();
        market.put("TCS", new Stock("TCS", 3500));
        market.put("INFY", new Stock("INFY", 1500));
        market.put("RELIANCE", new Stock("RELIANCE", 2400));

        User user = loadUser();
        if (user == null) {
            System.out.print("Enter your name: ");
            String name = sc.nextLine();
            user = new User(name, 100000); // Initial balance
        }

        while (true) {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Save & Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Market Data ---");
                    for (Stock s : market.values()) {
                        System.out.println(s.symbol + " : ₹" + s.price);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();
                    if (market.containsKey(buySymbol))
                        user.buyStock(market.get(buySymbol), buyQty);
                    else
                        System.out.println("Invalid stock!");
                    break;

                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();
                    if (market.containsKey(sellSymbol))
                        user.sellStock(market.get(sellSymbol), sellQty);
                    else
                        System.out.println("Invalid stock!");
                    break;

                case 4:
                    user.showPortfolio(market);
                    break;

                case 5:
                    saveUser(user);
                    System.out.println("Data saved. Thank you!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
