package collections;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ComparatorMap implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Map<String,Object> map1=(Map<String, Object>) o1;
        Map<String,Object> map2=(Map<String, Object>) o2;
        Object year = map1.get("year");
        String a1 = year.toString();
        String a2 = map2.get("year").toString();
        int i = a1.compareTo(a2);
        return i;
    }
}
