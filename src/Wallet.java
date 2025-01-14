import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet implements Serializable {
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
            incomeByCategory.put("Без категории", incomeByCategory.getOrDefault("Без категории", 0.0) + amount);
        }
    }

    public void addExpense(String category, double amount) {
        if (category != null && !category.isEmpty()) {
            expensesByCategory.put(category, expensesByCategory.getOrDefault(category, 0.0) + amount);
        } else {
            expensesByCategory.put("Без категории", expensesByCategory.getOrDefault("Без категории", 0.0) + amount);
        }
        double totalExpenses = expensesByCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        if(totalExpenses < 0){
            System.out.println("Вы ушли в минус по данной категории!");
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
                double remaining = budget - spent + received;
                System.out.println(category + ": " + budget + ", Остаток: " + remaining);
            }
        }
    }
    public void calculateTotalsForCategories(String[] categories) {
        double totalIncome = 0;
        double totalExpenses = 0;

        for (String category : categories) {
            Double income = incomeByCategory.get(category);
            Double expense = expensesByCategory.get(category);

            if(income != null) {
                totalIncome += income;
            }
            else {
                System.out.println("Одной из категорий не существует, отмена операции");
                return;
            }
            if(expense != null) {
                totalExpenses += expensesByCategory.get(category);
            }
            else {
                System.out.println("Одной из категорий не существует, отмена операции");
                return;
            }
//            totalIncome += incomeByCategory.getOrDefault(category, 0.0);
//            totalExpenses += expensesByCategory.getOrDefault(category, 0.0);
        }

        System.out.println("Суммарный доход: " + totalIncome);
        System.out.println("Суммарные расходы: " + totalExpenses);
    }
}