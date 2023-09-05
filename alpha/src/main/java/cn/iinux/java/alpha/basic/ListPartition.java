package cn.iinux.java.alpha.basic;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ListPartition {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        List<List<Integer>> p = Lists.partition(list, 11);
        p.forEach(System.out::println);
    }
}
