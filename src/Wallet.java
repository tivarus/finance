import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
    //List<Income> incomes = new ArrayList<>();
    //List<Expense> expenses = new ArrayList<>();
    //Map<String, Double> budget = new HashMap<>();

    Map<String, Double> incomeByCategory = new HashMap<>();
    Map<String, Double> expensesByCategory = new HashMap<>();
    Map<String, Double> budgetByCategory = new HashMap<>();

    public void addIncome(String category, double amount) {
        if (category != null && !category.isEmpty()) {
            incomeByCategory.put(category, incomeByCategory.getOrDefault(category, 0.0) + amount);
        } else {
            // Обработка дохода без категории, если это необходимо
            incomeByCategory.put("Без категории", incomeByCategory.getOrDefault("Без категории", 0.0) + amount);
        }
    }

    public void addExpense(String category, double amount) {
        if (category != null && !category.isEmpty()) {
            expensesByCategory.put(category, expensesByCategory.getOrDefault(category, 0.0) + amount);
        } else {
            // Обработка расхода без категории, если это необходимо
            expensesByCategory.put("Без категории", expensesByCategory.getOrDefault("Без категории", 0.0) + amount);
        }
    }

    void setBudget(String category, double amount) {
        budgetByCategory.put(category, amount);
    }

//    void checkBudget(String category) {
//        if (budget.containsKey(category)) {
//            double totalExpenses = expenses.stream()
//                    .filter(expense -> expense.category.equals(category))
//                    .mapToDouble(expense -> expense.amount)
//                    .sum();
//            double categoryBudget = budget.get(category);
//            if (totalExpenses > categoryBudget) {
//                System.out.println("Warning: Budget exceeded for category: " + category);
//            }
//        }
//    }



//    double getTotalExpense() {
//        return expenses.stream().mapToDouble(expense -> expense.amount).sum();
//    }
//    double getTotalIncome() {
//        return incomes.stream().mapToDouble(income ->income.amount).sum();
//    }
//    void displaySummary() {
//        System.out.println("Total Income: " + getTotalIncome());
//        System.out.println("Total Expense: " + getTotalExpense());
//        for (String category : budget.keySet()) {
//            double totalCategoryExpense = expenses.stream()
//                    .filter(expense -> expense.category.equals(category))
//                    .mapToDouble(expense -> expense.amount).sum();
//            double remainingBudget = budget.get(category) - totalCategoryExpense;
//            System.out.println(category + ": Budget: " + budget.get(category) + ", Remaining: " + remainingBudget);
//        }
//    }
public void displaySummary() {
    double totalIncome = incomeByCategory.values().stream().mapToDouble(Double::doubleValue).sum();
    double totalExpenses = expensesByCategory.values().stream().mapToDouble(Double::doubleValue).sum();

    System.out.println("Общий доход: " + totalIncome);
    System.out.println("Общие расходы: " + totalExpenses);

    System.out.println("Доходы по категориям:");
    for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }

    System.out.println("Расходы по категориям:");
    for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }

    System.out.println("Бюджеты по категориям:");
    for (Map.Entry<String, Double> entry : budgetByCategory.entrySet()) {
        String category = entry.getKey();
        double budget = entry.getValue();
        double spent = expensesByCategory.getOrDefault(category, 0.0);
        double received = incomeByCategory.getOrDefault(category, 0.0);
        double remaining = budget - spent + received;
        System.out.println(category + ": " + budget + ", Остаток: " + remaining);
    }
}
}