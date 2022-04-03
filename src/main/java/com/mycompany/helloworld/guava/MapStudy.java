package com.mycompany.helloworld.guava;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.*;

public class MapStudy {
    @Test
    public void testTable() {
        Table<String, String, Integer> table = HashBasedTable.create();
        // 存放元素
        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);

        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);

        // 取出元素
        Integer dayCount = table.get("Hydra", "Feb");
        System.out.println(dayCount);

        // rowKey或columnKey的集合
        Set<String> rowKeys = table.rowKeySet();
        Set<String> columnKeys = table.columnKeySet();

        // value集合
        Collection<Integer> values = table.values();
        System.out.println(rowKeys);
        System.out.println(columnKeys);
        System.out.println(values);

        for (String key : table.rowKeySet()) {
            Set<Map.Entry<String, Integer>> rows = table.row(key).entrySet();
            int total = 0;
            for (Map.Entry<String, Integer> row : rows) {
                total += row.getValue();
            }
            System.out.println(key + ": " + total);
        }

        Table<String, String, Integer> table2 = Tables.transpose(table);
        Set<Table.Cell<String, String, Integer>> cells = table2.cellSet();
        cells.forEach(cell ->
                System.out.println(cell.getRowKey() + "," + cell.getColumnKey() + ":" + cell.getValue())
        );

        Map<String, Map<String, Integer>> rowMap = table.rowMap();
        Map<String, Map<String, Integer>> columnMap = table.columnMap();
        System.out.println(rowMap);
        System.out.println(columnMap);
    }

    @Test
    public void testBiMap() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("Hydra", "Programmer");
        biMap.put("Tony", "IronMan");
        biMap.put("Thanos", "Titan");

        // will cause Exception
        // biMap.put("Thanos1", "Titan");
        biMap.forcePut("Thanos1", "Titan");

        // 使用key获取value
        System.out.println(biMap.get("Tony"));

        BiMap<String, String> inverse = biMap.inverse();
        // 使用value获取key
        System.out.println(inverse.get("Titan"));

        System.out.println(biMap.get("Stark"));
        inverse.put("IronMan", "Stark");
        System.out.println(biMap.get("Stark"));

        System.out.println(biMap.values());
    }

    @Test
    public void testMultiMap() {
        Multimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("day", 1);
        multimap.put("day", 2);
        multimap.put("day", 8);
        multimap.put("month", 3);

        System.out.println(multimap);

        Map<String, Collection<Integer>> map = multimap.asMap();
        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
        map.get("day").add(20);
        System.out.println(multimap);


        System.out.println(multimap.size());
        System.out.println(multimap.entries().size());
        for (Map.Entry<String, Integer> entry : multimap.entries()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
        System.out.println(multimap.keySet().size());
        Set<Map.Entry<String, Collection<Integer>>> entries = multimap.asMap().entrySet();
        System.out.println(entries.size());
    }

    @Test
    public void testRangeMap() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closedOpen(0, 60), "fail");
        rangeMap.put(Range.closed(60, 90), "satisfactory");
        rangeMap.put(Range.openClosed(90, 100), "excellent");

        System.out.println(rangeMap.get(59));
        System.out.println(rangeMap.get(60));
        System.out.println(rangeMap.get(90));
        System.out.println(rangeMap.get(91));

        rangeMap.remove(Range.closed(70, 80));
        System.out.println(rangeMap.get(75));
        System.out.println(rangeMap);
    }

    @Test
    public void classToInstanceMap() {
        ClassToInstanceMap<Object> instanceMap = MutableClassToInstanceMap.create();
        User user = new User("Hydra", 18);
        Dept dept = new Dept("develop", 200);

        instanceMap.putInstance(User.class, user);
        instanceMap.putInstance(Dept.class, dept);

        User user1 = instanceMap.getInstance(User.class);
        System.out.println(user == user1);

        ClassToInstanceMap<Map> instanceMap2 = MutableClassToInstanceMap.create();
        HashMap<String, Object> hashMap = new HashMap<>();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        ArrayList<Object> list = new ArrayList<>();

        instanceMap2.putInstance(HashMap.class, hashMap);
        instanceMap2.putInstance(TreeMap.class, treeMap);
        // instanceMap2.putInstance(ArrayList.class, list);
    }

    private static class User {
        User(String name, int val) {

        }
    }

    private static class Dept {
        Dept(String name, int val) {

        }
    }
}
