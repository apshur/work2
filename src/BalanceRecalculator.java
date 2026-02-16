import java.math.BigDecimal;
import java.util.List;

final class BalanceRecalculator {
    private final BankAccountRepository accounts;
    private final OperationRepository operations;

    BalanceRecalculator(BankAccountRepository accounts, OperationRepository operations) {
        this.accounts = accounts;
        this.operations = operations;
    }

    BigDecimal recalc(String accountId) {
        BankAccount acc = accounts.get(accountId);
        BigDecimal balance = BigDecimal.ZERO;

        List<Operation> ops = operations.listByAccount(accountId);
        for (Operation o : ops) {
            BigDecimal effect = (o.getType() == OperationType.INCOME) ? o.getAmount() : o.getAmount().negate();
            balance = balance.add(effect);
        }

        acc.setBalance(balance);
        return balance;
    }
}
