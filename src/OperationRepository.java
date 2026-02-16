import java.util.List;

interface OperationRepository {
    void add(Operation o);
    Operation get(String id);
    List<Operation> listAll();
    void delete(String id);
    List<Operation> listByAccount(String accountId);
}
