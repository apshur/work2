import java.util.*;

final class Stats {
    private final Map<String, List<Long>> ns = new LinkedHashMap<String, List<Long>>();

    AutoCloseable timer(final String name) {
        final long t0 = System.nanoTime();
        return new AutoCloseable() {
            public void close() {
                long dt = System.nanoTime() - t0;
                List<Long> list = ns.get(name);
                if (list == null) {
                    list = new ArrayList<Long>();
                    ns.put(name, list);
                }
                list.add(dt);
            }
        };
    }

    void print() {
        for (Map.Entry<String, List<Long>> e : ns.entrySet()) {
            String name = e.getKey();
            List<Long> v = e.getValue();
            long total = 0;
            long min = Long.MAX_VALUE;
            long max = 0;
            for (Long x : v) {
                total += x;
                if (x < min) min = x;
                if (x > max) max = x;
            }
            double avgMs = (v.isEmpty() ? 0.0 : (total / 1_000_000.0) / v.size());
            System.out.println(name + " -> runs=" + v.size()
                    + ", avgMs=" + String.format(java.util.Locale.US, "%.3f", avgMs)
                    + ", minMs=" + (min / 1_000_000.0)
                    + ", maxMs=" + (max / 1_000_000.0));
        }
    }
}
