import java.util.*;

public class Symbol_Table {
    private final Map<String,String> table = new HashMap<>();

    public void declare(String name,String type) {
        table.put(name,type);
    }

    public String get(String name) {
        if (!table.containsKey(name))
            throw new RuntimeException("Undeclared variable: "+name);
        return table.get(name);
    }

    public Map<String,String> getAll() {
        return table;
    }
}
