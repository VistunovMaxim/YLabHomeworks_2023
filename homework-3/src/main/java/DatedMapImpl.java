import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap {

    Map map;
    Map dateMap;
    Date date;

    public DatedMapImpl() {
        map = new HashMap<String, String>();
        dateMap = new HashMap<String, Date>();
        date = new Date();
    }

    @Override
    public void put(String key, String value) {
        map.put(key, value);
        dateMap.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return !map.containsKey(key) ? null : map.get(key).toString();
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
        dateMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return !map.containsKey(key) ? null : (Date) dateMap.get(key);
    }
}
