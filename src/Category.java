final class Category {
    private final String id;
    private final OperationType type;
    private String name;

    Category(String id, OperationType type, String name) {
        this.id = Validator.requireNonBlank(id, "Category.id");
        if (type == null) throw new IllegalArgumentException("Category.type is required");
        this.type = type;
        this.name = Validator.requireNonBlank(name, "Category.name");
    }

    String getId() { return id; }
    OperationType getType() { return type; }
    String getName() { return name; }

    void setName(String name) { this.name = Validator.requireNonBlank(name, "Category.name"); }

    @Override
    public String toString() {
        return "Category{id='" + id + "', type=" + type + ", name='" + name + "'}";
    }
}
