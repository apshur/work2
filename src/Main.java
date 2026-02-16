import java.math.BigDecimal;
import java.time.LocalDate;

public final class Main {
    public static void main(String[] args) throws Exception {
        Container c = AppFactory.build();

        FinanceService finance = c.get(FinanceService.class);
        AnalyticsService analytics = c.get(AnalyticsService.class);
        BalanceRecalculator recalc = c.get(BalanceRecalculator.class);
        Stats stats = c.get(Stats.class);

        try (AutoCloseable t = stats.timer("create-data")) {
            finance.createAccount("acc1", "Основной счет", BigDecimal.ZERO);
            finance.createCategory("salary", OperationType.INCOME, "Зарплата");
            finance.createCategory("cafe", OperationType.EXPENSE, "Кафе");

            finance.addOperation("op1", OperationType.INCOME, "acc1", "salary",
                    new BigDecimal("100000"), LocalDate.of(2026, 2, 1), "Февраль");
            finance.addOperation("op2", OperationType.EXPENSE, "acc1", "cafe",
                    new BigDecimal("850"), LocalDate.of(2026, 2, 3), "Латте");
            finance.addOperation("op3", OperationType.EXPENSE, "acc1", "cafe",
                    new BigDecimal("1200"), LocalDate.of(2026, 2, 5), "Обед");
        }

        System.out.println("=== Accounts ===");
        for (BankAccount a : finance.listAccounts()) System.out.println(a);

        System.out.println("\n=== Operations ===");
        for (Operation o : finance.listOperations()) System.out.println(o);

        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = LocalDate.of(2026, 2, 28);

        try (AutoCloseable t = stats.timer("analytics")) {
            BigDecimal income = analytics.income(start, end);
            BigDecimal expense = analytics.expense(start, end);
            System.out.println("\n=== Analytics ===");
            System.out.println("income=" + income + " expense=" + expense + " net=" + analytics.net(start, end));
            System.out.println("expenseByCategory=" + analytics.byCategory(start, end, OperationType.EXPENSE));
        }

        try (AutoCloseable t = stats.timer("recalc")) {
            System.out.println("\n=== Recalc balance acc1 ===");
            System.out.println("newBalance=" + recalc.recalc("acc1"));
        }

        System.out.println("\n=== Stats ===");
        stats.print();
    }
}
