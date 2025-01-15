import java.io.*;
import java.util.Scanner;

public class Main {
    static User currentUser = null;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String command;
        User.loadUserProfiles("user_profiles.dat");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("Введите команду (reg/log/exit): ");
            command = scanner.nextLine();

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
                    System.out.println("Ошибка: Неизвестная команда.");
            }
            if (currentUser != null) {
                manageFinance(reader);
            }
        }
    }

    static void registerUser(Scanner reader) throws IOException {
        System.out.println("Введите логин: ");
        String username = reader.nextLine();
        System.out.println("Введите пароль: ");
        String password = reader.nextLine();
        if (!User.users.containsKey(username)) {
            User.users.put(username, new User(username, password));
            System.out.println("Регистрация успешно завершена.");
        } else {
            System.out.println("Ошибка: Такой пользователь уже есть");
        }
    }

    static void loginUser(Scanner reader) throws IOException {
        System.out.println("Введите логин: ");
        String username = reader.nextLine();
        System.out.println("Введите пароль: ");
        String password = reader.nextLine();
        User user = User.users.get(username);
        if (user != null && user.password.equals(password)) {
            currentUser = user;
            System.out.println("Вход выполнен.");
        } else {
            System.out.println("Ошибка: Неверные данные.");
        }
    }

    static void manageFinance(Scanner reader) throws IOException {
        String command;
        while (true) {
            System.out.println("Введите команду (addIncome/addExpense/setBudget/showAll/showBudgets/exit): ");
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "addincome":
                    double incomeValue = 0;
                    System.out.println("Введите категорию: ");
                    String incomeCategory = scanner.nextLine();
                    System.out.println("Введите сумму: ");
                    if (scanner.hasNextDouble()) {
                        incomeValue = scanner.nextDouble();
                    } else {
                        System.out.println("Ошибка: Введите положительное число.");
                    }
                    currentUser.wallet.addIncome(incomeCategory, incomeValue);
                    break;
                case "addexpense":
                    double expenseValue = 0;
                    System.out.println("Введите категорию: ");
                    String expenseCategory = scanner.nextLine();
                    System.out.println("Введите сумму: ");
                    if (scanner.hasNextDouble()) {
                        expenseValue = scanner.nextDouble();
                    } else {
                        System.out.println("Ошибка: Введите положительное число.");
                    }
                    currentUser.wallet.addExpense(expenseCategory, expenseValue);
                    break;
                case "setbudget":
                    double budgetValue = 0;
                    System.out.println("Введите категорию: ");
                    String budgetCategory = reader.nextLine();
                    System.out.println("Введите лимит бюджета: ");
                    if (scanner.hasNextDouble()) {
                        budgetValue = scanner.nextDouble();
                    } else {
                        System.out.println("Ошибка: Введите положительное число.");
                    }
                    currentUser.wallet.setBudget(budgetCategory, budgetValue);
                    break;
                case "showall":
                    currentUser.wallet.displaySummary();
                    break;
                case "showbudgets":
                    System.out.println("Введите категории через запятую(a, b, c...): ");
                    String budgetCategories = reader.nextLine();
                    String[] categories = budgetCategories.split(",");

                    for (int i = 0; i < categories.length; i++) {
                        categories[i] = categories[i].trim();
                    }

                    currentUser.wallet.calcSelectedBudgets(categories);
                    break;
                case "exit":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Ошибка: Неверные данные.");
            }
        }
    }
}