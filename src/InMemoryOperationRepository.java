import java.util.*;

final class InMemoryOperationRepository implements OperationRepository {
    private final Map<String, Operation> data = new LinkedHashMap<String, Operation>();

    public void add(Operation o) { data.put(o.getId(), o); }

    public Operation get(String id) {
        Operation o = data.get(id);
        if (o == null) throw new NoSuchElementException("Operation not found: " + id);
        return o;
    }

    public List<Operation> listAll() { return new ArrayList<Operation>(data.values()); }

    public void delete(String id) { data.remove(id); }

    public List<Operation> listByAccount(String accountId) {
        List<Operation> out = new ArrayList<Operation>();
        for (Operation o : data.values()) {
            if (o.getAccountId().equals(accountId)) out.add(o);
        }
        return out;
    }
}
