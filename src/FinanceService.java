import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

final class FinanceService {
    private final BankAccountRepository accounts;
    private final CategoryRepository categories;
    private final OperationRepository operations;

    FinanceService(BankAccountRepository accounts, CategoryRepository categories, OperationRepository operations) {
        this.accounts = accounts;
        this.categories = categories;
        this.operations = operations;
    }

    BankAccount createAccount(String id, String name, BigDecimal balance) {
        BankAccount a = new BankAccount(id, name, balance);
        accounts.add(a);
        return a;
    }

    Category createCategory(String id, OperationType type, String name) {
        Category c = new Category(id, type, name);
        categories.add(c);
        return c;
    }

    Operation addOperation(String id, OperationType type, String accountId, String categoryId,
                           BigDecimal amount, LocalDate date, String description) {
        BankAccount acc = accounts.get(accountId);
        Category cat = categories.get(categoryId);

        if (cat.getType() != type) {
            throw new IllegalArgumentException("Category type must match operation type");
        }

        Operation op = new Operation(id, type, accountId, categoryId, amount, date, description);
        operations.add(op);

        BigDecimal effect = (type == OperationType.INCOME) ? amount : amount.negate();
        acc.setBalance(acc.getBalance().add(effect));

        return op;
    }

    void deleteOperation(String operationId) {
        Operation op = operations.get(operationId);
        BankAccount acc = accounts.get(op.getAccountId());

        BigDecimal effect = (op.getType() == OperationType.INCOME) ? op.getAmount() : op.getAmount().negate();
        acc.setBalance(acc.getBalance().subtract(effect));

        operations.delete(operationId);
    }

    List<BankAccount> listAccounts() { return accounts.listAll(); }
    List<Category> listCategories() { return categories.listAll(); }
    List<Operation> listOperations() { return operations.listAll(); }
}
