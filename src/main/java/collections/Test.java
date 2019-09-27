package collections;

import java.util.*;


public class Test {

    public static void main(String[] args) {

        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map1=new HashMap<>();
        Map<String,Object> map2=new HashMap<>();
        Map<String,Object> map3=new HashMap<>();
        map1.put("year","11");
        list.add(map1);
        map2.put("year","222");
        list.add(map2);
        map3.put("year","33");
        list.add(map3);
        System.out.println(list);
        ComparatorMap comparatorMap=new ComparatorMap();
        List list1=new ArrayList();
        list1.add(2);
        list1.add(3);
        list1.add(1);
        System.out.println(list1);
        Collections.sort(list1, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return o2.toString().compareTo(o1.toString());
            }
        });
        System.out.println(list1);
        System.out.println(list);

    }
}
