package com.mycompany.helloworld;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapNullKeyTest {
    @Test
    public void withNull() {
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put(null, null);
        System.out.println(hashmap);
    }

    @Test
    public void withNull2() {
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put(null, null);
        System.out.println(treeMap);
    }
}
