import java.util.HashMap;
import java.util.Map;

final class Container {
    interface Provider<T> { T get(); }

    private final Map<Class<?>, Provider<?>> providers = new HashMap<Class<?>, Provider<?>>();
    private final Map<Class<?>, Object> singletons = new HashMap<Class<?>, Object>();

    <T> void register(Class<T> type, Provider<T> provider) {
        providers.put(type, provider);
    }

    @SuppressWarnings("unchecked")
    <T> T get(Class<T> type) {
        Object existing = singletons.get(type);
        if (existing != null) return (T) existing;

        Provider<?> p = providers.get(type);
        if (p == null) throw new IllegalStateException("No provider for " + type.getName());

        Object created = p.get();
        singletons.put(type, created);
        return (T) created;
    }
}