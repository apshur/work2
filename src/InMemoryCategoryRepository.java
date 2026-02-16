import java.util.*;

final class InMemoryCategoryRepository implements CategoryRepository {
    private final Map<String, Category> data = new LinkedHashMap<String, Category>();

    public void add(Category c) { data.put(c.getId(), c); }

    public Category get(String id) {
        Category c = data.get(id);
        if (c == null) throw new NoSuchElementException("Category not found: " + id);
        return c;
    }

    public List<Category> listAll() { return new ArrayList<Category>(data.values()); }

    public void delete(String id) { data.remove(id); }
}
