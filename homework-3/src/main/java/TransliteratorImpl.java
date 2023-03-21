import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator {

    @Override
    public String transliterate(String source) {
//        source = source.toUpperCase();
        Map<Integer, String> map = initializeMap(new HashMap<>());

        for (int i = 0; i < source.length(); i++) {
            if (map.containsKey((int) source.charAt(i))) {
                source = source.substring(0, i) + map.get((int) source.charAt(i)) + source.substring(i + 1);
            }
        }
        return source;
    }

    public Map<Integer, String> initializeMap(Map<Integer, String> map) {
        int j = 0;
        String translit = "ABVGDEZHZIIKLMNOPRSTUFKHTSCHSHSHCHIEY-EIUIA";

        map.put(1025, "E");

        for (int i = 1040; i < 1072; i++) {
            if (i == 1046 || i == 1061 || i == 1062 || i == 1063 || i == 1064 || i == 1066 || i == 1070 || i == 1071) {
                map.put(i, translit.charAt(j) + "" + translit.charAt(j + 1));
                j += 2;
            } else if (i == 1065) {
                map.put(i, translit.charAt(j) + "" + translit.charAt(j + 1) + "" + translit.charAt(j + 2) + "" +
                        translit.charAt(j + 3));
                j += 4;
            } else {
                map.put(i, String.valueOf(translit.charAt(j)));
                j++;
            }
        }
        return map;
    }

}
