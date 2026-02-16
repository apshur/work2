import java.util.List;

interface CategoryRepository {
    void add(Category c);
    Category get(String id);
    List<Category> listAll();
    void delete(String id);
}
