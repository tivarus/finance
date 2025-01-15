import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Wallet implements Serializable {

    Map<String, Double> incomeByCategory = new HashMap<>();
    Map<String, Double> expensesByCategory = new HashMap<>();
    Map<String, Double> budgetByCategory = new HashMap<>();

    public void addIncome(String category, double amount) {
        if (amount < 0) {
            System.out.println("Ошибка:Введите положительное число");
            return;
        }
        if (category != null && !category.isEmpty()) {
            incomeByCategory.put(category, incomeByCategory.getOrDefault(category, 0.0) + amount);
        } else {
            incomeByCategory.put("Без категории", incomeByCategory.getOrDefault("Без категории", 0.0) + amount);
        }
    }

    public void addExpense(String category, double amount) {
        if (amount < 0) {
            System.out.println("Ошибка:Введите положительное число");
            return;
        }
        if (category != null && !category.isEmpty()) {
            expensesByCategory.put(category, expensesByCategory.getOrDefault(category, 0.0) + amount);
            if (incomeByCategory.get(category) - expensesByCategory.get(category) < 0) {
                System.out.println("Внимание:Вы ушли в минус по данной категории!");
            }
            if (budgetByCategory.equals(category)) {
                double budget = budgetByCategory.get(category);
                double spent = expensesByCategory.getOrDefault(category, 0.0);
                double received = incomeByCategory.getOrDefault(category, 0.0);
                if (budget + received - spent < 0) {
                    System.out.println("Внимание:Вы ушли в минус по данному бюджету!");
                }
            }
        } else {
            expensesByCategory.put("Без категории", expensesByCategory.getOrDefault("Без категории", 0.0) + amount);
        }

        double balance = incomeByCategory.values().stream().mapToDouble(Double::doubleValue).sum() -
                expensesByCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        if (balance < 0) {
            System.out.println("Внимание: Расходы превышают доходы!");
        }
    }

    void setBudget(String category, double amount) {
        if (amount < 0) {
            System.out.println("Ошибка:Введите положительное число");
            return;
        }
        if (category != null && !category.isEmpty() && !budgetByCategory.equals(category)) {
            budgetByCategory.put(category, budgetByCategory.getOrDefault(category, 0.0) + amount);
            incomeByCategory.put(category, incomeByCategory.getOrDefault(category, 0.0) + amount);
        } else {
            System.out.println("Ошибка:Введите новую категорию");
        }
    }

    public void displaySummary() {
        double totalIncome = incomeByCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalExpenses = expensesByCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        if (!incomeByCategory.isEmpty()) {
            System.out.println("Общий доход: " + totalIncome);
        }
        if (!expensesByCategory.isEmpty()) {
            System.out.println("Общие расходы: " + totalExpenses);
        }
        if (!incomeByCategory.isEmpty()) {
            System.out.println("Доходы по категориям:");
            for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
        if (!expensesByCategory.isEmpty()) {
            System.out.println("Расходы по категориям:");
            for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
        if (!budgetByCategory.isEmpty()) {
            System.out.println("Бюджеты по категориям:");
            for (Map.Entry<String, Double> entry : budgetByCategory.entrySet()) {
                String category = entry.getKey();
                double budget = entry.getValue();
                double spent = expensesByCategory.getOrDefault(category, 0.0);
                double received = incomeByCategory.getOrDefault(category, 0.0);
                double remaining = received - spent;
                System.out.println(category + ": " + budget + ", Остаток: " + remaining);
            }
        }
    }

    public void calcSelectedBudgets(String[] categories) {
        for (String category : categories) {
            if (budgetByCategory.equals(category)) {
                double budget = budgetByCategory.get(category);
                double spent = expensesByCategory.getOrDefault(category, 0.0);
                double received = incomeByCategory.getOrDefault(category, 0.0);
                double remaining = budget - spent + received;
                System.out.println(category + ": " + budget + ", Остаток: " + remaining);
            } else {
                System.out.println("Ошибка: Категория" + category + "не существует");
            }
        }
    }
}