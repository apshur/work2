import java.util.List;

interface BankAccountRepository {
    void add(BankAccount a);
    BankAccount get(String id);
    List<BankAccount> listAll();
    void delete(String id);
}
