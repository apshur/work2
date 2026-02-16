import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

final class AnalyticsService {
    private final OperationRepository operations;
    private final CategoryRepository categories;

    AnalyticsService(OperationRepository operations, CategoryRepository categories) {
        this.operations = operations;
        this.categories = categories;
    }

    BigDecimal income(LocalDate start, LocalDate end) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Operation o : operations.listAll()) {
            if (between(o.getDate(), start, end) && o.getType() == OperationType.INCOME) {
                sum = sum.add(o.getAmount());
            }
        }
        return sum;
    }

    BigDecimal expense(LocalDate start, LocalDate end) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Operation o : operations.listAll()) {
            if (between(o.getDate(), start, end) && o.getType() == OperationType.EXPENSE) {
                sum = sum.add(o.getAmount());
            }
        }
        return sum;
    }

    BigDecimal net(LocalDate start, LocalDate end) {
        return income(start, end).subtract(expense(start, end));
    }

    Map<String, BigDecimal> byCategory(LocalDate start, LocalDate end, OperationType type) {
        Map<String, BigDecimal> totals = new HashMap<String, BigDecimal>();

        for (Operation o : operations.listAll()) {
            if (!between(o.getDate(), start, end)) continue;
            if (o.getType() != type) continue;

            String name = categories.get(o.getCategoryId()).getName();
            BigDecimal cur = totals.get(name);
            totals.put(name, (cur == null ? BigDecimal.ZERO : cur).add(o.getAmount()));
        }

        List<Map.Entry<String, BigDecimal>> list = new ArrayList<Map.Entry<String, BigDecimal>>(totals.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, BigDecimal>>() {
            public int compare(Map.Entry<String, BigDecimal> a, Map.Entry<String, BigDecimal> b) {
                return b.getValue().compareTo(a.getValue());
            }
        });

        Map<String, BigDecimal> out = new LinkedHashMap<String, BigDecimal>();
        for (Map.Entry<String, BigDecimal> e : list) out.put(e.getKey(), e.getValue());
        return out;
    }

    private static boolean between(LocalDate d, LocalDate start, LocalDate end) {
        return (d.isEqual(start) || d.isAfter(start)) && (d.isEqual(end) || d.isBefore(end));
    }
}
