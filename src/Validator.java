final class Validator {
    private Validator() {}

    static String requireNonBlank(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return value.trim();
    }
}
