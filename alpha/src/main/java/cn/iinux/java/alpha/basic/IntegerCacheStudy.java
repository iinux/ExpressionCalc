package cn.iinux.java.alpha.basic;

public class IntegerCacheStudy {
    public static void main(String[] args) {
        int i = 10;
        int i1 = 10;
        Integer in1 = 10;
        Integer in2 = 10;
        Integer in3 = new Integer(10);
        Integer in4 = new Integer(10);
        Integer in5 = 199;
        Integer in6 = 199;

        System.out.println(i == i1);		// true
        System.out.println(i == in1);		// true
        System.out.println(i == in2);		// true
        System.out.println(i == in3);		// true
        System.out.println(in1 == in2);		// true

        // can change by vm option -Djava.lang.Integer.IntegerCache.high=200
        System.out.println(in5 == in6);		// false

        System.out.println(in1 == in3);		// false
        System.out.println(in3 == in4);		// false
    }
}
