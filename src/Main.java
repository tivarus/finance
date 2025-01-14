import java.io.*;
import java.util.*;

public class Main {
    static User currentUser = null;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;
        User.loadUsers();

        while (true) {
            System.out.println("Enter command (reg/login/exit): ");
            command = reader.readLine();

            switch (command.toLowerCase()) {
                case "reg":
                    registerUser(reader);
                    break;
                case "login":
                    loginUser(reader);
                    break;
                case "exit":
                    User.saveUsers();
                    System.exit(0);
                default:
                    System.out.println("Unknown command.");
            }

            if (currentUser != null) {
                manageFinance(reader);
            }
        }
    }

    static void registerUser(BufferedReader reader) throws IOException {
        System.out.println("Enter username: ");
        String username = reader.readLine();
        System.out.println("Enter password: ");
        String password = reader.readLine();
        User.users.put(username, new User(username, password));
        System.out.println("User registered successfully.");
    }

    static void loginUser(BufferedReader reader) throws IOException {
        System.out.println("Enter username: ");
        String username = reader.readLine();
        System.out.println("Enter password: ");
        String password = reader.readLine();
        User user = User.users.get(username);
        if (user != null && user.password.equals(password)) {
            currentUser = user;
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    static void manageFinance(BufferedReader reader) throws IOException {
        String command;
        while (true) {
            System.out.println("Enter finance command (addIncome/addExpense/setBudget/show/exit): ");
            command = reader.readLine();

            switch (command.toLowerCase()) {
                case "addincome":
                    System.out.println("Enter source: ");
                    String incomeSource = reader.readLine();
                    System.out.println("Enter amount: ");
                    double incomeAmount = Double.parseDouble(reader.readLine());
                    currentUser.wallet.addIncome(incomeSource, incomeAmount);
                    break;
                case "addexpense":
                    System.out.println("Enter category: ");
                    String expenseCategory = reader.readLine();
                    System.out.println("Enter amount: ");
                    double expenseAmount = Double.parseDouble(reader.readLine());
                    currentUser.wallet.addExpense(expenseCategory, expenseAmount);
                    break;
                case "setbudget":
                    System.out.println("Enter category: ");
                    String budgetCategory = reader.readLine();
                    System.out.println("Enter budget amount: ");
                    double budgetAmount = Double.parseDouble(reader.readLine());
                    currentUser.wallet.setBudget(budgetCategory, budgetAmount);
                    break;
                case "show":
                    currentUser.wallet.displaySummary();
                    break;
                case "exit":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}