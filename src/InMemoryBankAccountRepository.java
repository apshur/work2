import java.util.*;

final class InMemoryBankAccountRepository implements BankAccountRepository {
    private final Map<String, BankAccount> data = new LinkedHashMap<String, BankAccount>();

    public void add(BankAccount a) { data.put(a.getId(), a); }

    public BankAccount get(String id) {
        BankAccount a = data.get(id);
        if (a == null) throw new NoSuchElementException("Account not found: " + id);
        return a;
    }

    public List<BankAccount> listAll() { return new ArrayList<BankAccount>(data.values()); }

    public void delete(String id) { data.remove(id); }
}
