package technology.assessment.app.model.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum AccountType {
    EMPLOYEE("Employee", 0.3),
    CUSTOMER("Customer", 0.25),
    AFFILIATE("Affiliate", 0.1),
    OTHERS("Others", 100.);

    public final String label;
    public final Double discountRate;
    private static final Map<String, AccountType> map = new HashMap<>();


    static {
        for (AccountType e : values()) {
            map.put(e.label, e);
        }
    }

    private AccountType(String label, Double discountRate) {
        this.label = label;
        this.discountRate = discountRate;

    }

    public static AccountType valueOfName(String label) {
        return map.get(label);
    }

    public static List<String> list() {
        return map.values().stream().map(rs -> rs.label).collect(Collectors.toList());
    }
}
