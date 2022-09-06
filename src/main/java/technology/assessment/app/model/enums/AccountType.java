package technology.assessment.app.model.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum AccountType {
    EMPLOYEE("Employee"),
    CUSTOMER("Customer"),
    AFFILIATE("Affiliate");

    public final String label;
    private static final Map<String, AccountType> map = new HashMap<>();


    static {
        for (AccountType e : values()) {
            map.put(e.label, e);
        }
    }
    /*//*/
    private AccountType(String label) {
        this.label=label;

    }
    public static AccountType valueOfName(String label) {
        return map.get(label);
    }
    public static List<String> list(){
        return map.values().stream().map(rs->rs.label).collect(Collectors.toList());
    }
}
