import java.io.*;
import java.util.Scanner;

public class Main {
    static User currentUser = null;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;
        User.loadUserProfiles("user_profiles.dat");
        while (true) {
            System.out.println("Введите команду (reg/log/exit): ");
            command = reader.readLine();

            switch (command.toLowerCase()) {
                case "reg":
                    registerUser(reader);
                    break;
                case "log":
                    loginUser(reader);
                    break;
                case "exit":
                    User.saveUserProfiles("user_profiles.dat");
                    System.exit(0);
                default:
                    System.out.println("Неизвестная команда.");
            }

            if (currentUser != null) {
                manageFinance(reader);
            }
        }
    }

    static void registerUser(BufferedReader reader) throws IOException {
        System.out.println("Введите логин: ");
        String username = reader.readLine();
        System.out.println("Введите пароль: ");
        String password = reader.readLine();
        User user = User.users.get(username);
        if(!User.users.containsKey(username)) {
            User.users.put(username, new User(username, password));
            System.out.println("Регистрация успешно завершена.");
        } else {
            System.out.println("Такой пользователь уже есть");
        }
    }

    static void loginUser(BufferedReader reader) throws IOException {
        System.out.println("Введите логин: ");
        String username = reader.readLine();
        System.out.println("Введите пароль: ");
        String password = reader.readLine();
        User user = User.users.get(username);
        if (user != null && user.password.equals(password)) {
            currentUser = user;
            System.out.println("Вход выполнен.");
        } else {
            System.out.println("Неверные данные.");
        }
    }

    static void manageFinance(BufferedReader reader) throws IOException {
        String command;
        while (true) {
            System.out.println("Введите команду (addIncome/addExpense/setBudget/show/budgetsum/exit): ");
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextLine();
            //command = reader.readLine();

            switch (command.toLowerCase()) {
                case "addincome":
                    double incomeAmount = 0;
                    System.out.println("Введите категорию: ");
                    //String incomeSource = reader.readLine();
                    String incomeSource = scanner.nextLine();
                    System.out.println("Введите сумму: ");
                    if (scanner.hasNextDouble()) {
                        incomeAmount = scanner.nextDouble();
                    } else {
                        System.out.println("Ошибка ввода. Пожалуйста, введите число.");
                    }
                    //double incomeAmount = Double.parseDouble(reader.readLine());
                    currentUser.wallet.addIncome(incomeSource, incomeAmount);
                    break;
                case "addexpense":
                    double expenseAmount = 0;
                    System.out.println("Введите категорию: ");
                    String expenseCategory = scanner.nextLine();
                    //String expenseCategory = reader.readLine();
                    System.out.println("Введите сумму: ");
                    //Scanner scanner = new Scanner(System.in);
                    if (scanner.hasNextDouble()) {
                        expenseAmount = scanner.nextDouble();
                    } else {
                        System.out.println("Ошибка ввода. Пожалуйста, введите число.");
                    }
                    //scanner.close();


                    //double expenseAmount = Double.parseDouble(reader.readLine());
                    currentUser.wallet.addExpense(expenseCategory, expenseAmount);
                    break;
                case "setbudget":
                    double budgetAmount = 0;
                    System.out.println("Введите категорию: ");
                    String budgetCategory = reader.readLine();
                    System.out.println("Введите лимит бюджета: ");
                    if (scanner.hasNextDouble()) {
                        budgetAmount = scanner.nextDouble();
                    } else {
                        System.out.println("Ошибка ввода. Пожалуйста, введите число.");
                    }
                    //double budgetAmount = Double.parseDouble(reader.readLine());
                    currentUser.wallet.setBudget(budgetCategory, budgetAmount);
                    break;
                case "show":
                    currentUser.wallet.displaySummary();
                    break;
                case "budgetsum":
                    System.out.println("Введите категории через запятую(a, b, c...): ");
                    String budgetCategories = reader.readLine();
                    String[] categories = budgetCategories.split(",");

                    for (int i = 0; i < categories.length; i++) {
                        categories[i] = categories[i].trim();
                    }
                    currentUser.wallet.calculateTotalsForCategories(categories);
                    break;
                case "exit":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Неверные данные.");
            }
        }
    }
}