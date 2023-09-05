package cn.iinux.java.alpha.basic;

import lombok.experimental.ExtensionMethod;
import lombok.var;

@ExtensionMethod({java.util.Arrays.class, Extensions.class})
public class ExtensionMethodExample {
    public static void main(String[] args) {
        var e = new ExtensionMethodExample();
        System.out.println(e.test());
    }

    public String test() {
        int[] intArray = {5, 3, 8, 2};
        String iAmNull = null;

        intArray.sort();
        return iAmNull.or("hELlO, WORlD!".toTitleCase());

        // java.util.Arrays.sort(intArray);
        // return Extensions.or(iAmNull, Extensions.toTitleCase("hELlO, WORlD!"));
    }
}

class Extensions {
    public static <T> T or(T obj, T ifNull) {
        return obj != null ? obj : ifNull;
    }

    public static String toTitleCase(String in) {
        if (in.isEmpty()) return in;
        return "" + Character.toTitleCase(in.charAt(0)) +
                in.substring(1).toLowerCase();
    }
}
