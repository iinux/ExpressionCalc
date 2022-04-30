package com.mycompany.helloworld.basic;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import java.util.List;
import java.util.Optional;

public class HashBasedTableTest {
    public static void main(String[] args) {
        List<List<Integer>> list = Lists.newArrayList(
                Lists.newArrayList(1, 5, 2, 4),
                Lists.newArrayList(2, 6, 4, 5),
                Lists.newArrayList(4, 7, 5, 6),
                Lists.newArrayList(4, 8, 6, 7)
        );
        validateListListSorted(list);
    }

    private static void validateListListSorted(List<List<Integer>> list) {
        Table<Integer, Integer, Integer> table = HashBasedTable.create();
        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            List<Integer> integers = list.get(i);
            for (int j = 0, integersSize = integers.size(); j < integersSize; j++) {
                Integer integer = integers.get(j);
                Integer temp = Optional.ofNullable(table.get(i - 1, j)).orElse(-1);
                Preconditions.checkArgument(temp < integer, "不是有序的");
                table.put(i, j, integer);
            }
        }

    }
}
