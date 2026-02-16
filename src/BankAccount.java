import java.math.BigDecimal;

final class BankAccount {
    private final String id;
    private String name;
    private BigDecimal balance;

    BankAccount(String id, String name, BigDecimal balance) {
        this.id = Validator.requireNonBlank(id, "BankAccount.id");
        this.name = Validator.requireNonBlank(name, "BankAccount.name");
        this.balance = balance == null ? BigDecimal.ZERO : balance;
    }

    String getId() { return id; }
    String getName() { return name; }
    BigDecimal getBalance() { return balance; }

    void setName(String name) { this.name = Validator.requireNonBlank(name, "BankAccount.name"); }
    void setBalance(BigDecimal balance) { this.balance = balance == null ? BigDecimal.ZERO : balance; }

    @Override
    public String toString() {
        return "BankAccount{id='" + id + "', name='" + name + "', balance=" + balance + "}";
    }
}
