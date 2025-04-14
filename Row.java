import java.util.HashMap;
import java.util.Map;

public class Row {
    private final Map<String, Object> data = new HashMap<>();

    public Row(String key, Object value) {
        data.put(key, value);
    }

    public Row add(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Object getValue(String key) {
        return data.get(key);
    }
}