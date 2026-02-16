import java.math.BigDecimal;
import java.time.LocalDate;

final class Operation {
    private final String id;
    private final OperationType type;
    private final String accountId;
    private final String categoryId;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description; // optional

    Operation(String id,
              OperationType type,
              String accountId,
              String categoryId,
              BigDecimal amount,
              LocalDate date,
              String description) {
        this.id = Validator.requireNonBlank(id, "Operation.id");
        if (type == null) throw new IllegalArgumentException("Operation.type is required");
        this.type = type;
        this.accountId = Validator.requireNonBlank(accountId, "Operation.accountId");
        this.categoryId = Validator.requireNonBlank(categoryId, "Operation.categoryId");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Operation.amount must be > 0");
        }
        this.amount = amount;
        if (date == null) throw new IllegalArgumentException("Operation.date is required");
        this.date = date;
        this.description = (description == null || description.trim().isEmpty()) ? null : description.trim();
    }

    String getId() { return id; }
    OperationType getType() { return type; }
    String getAccountId() { return accountId; }
    String getCategoryId() { return categoryId; }
    BigDecimal getAmount() { return amount; }
    LocalDate getDate() { return date; }
    String getDescription() { return description; }

    @Override
    public String toString() {
        return "Operation{id='" + id + "', type=" + type + ", accountId='" + accountId + "', categoryId='" + categoryId
                + "', amount=" + amount + ", date=" + date + ", description='" + description + "'}";
    }
}
