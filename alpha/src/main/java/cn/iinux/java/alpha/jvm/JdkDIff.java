package cn.iinux.java.alpha.jvm;

import java.net.URL;

public class JdkDIff {
    public static void main(String[] args) {
        URL r = ClassLoader.getSystemResource("java/lang/Class.class");
        System.out.println(r);
        // jdk 8
        // jar:file:/home/qzhang/Downloads/jdk8u312-b07/jre/lib/rt.jar!/java/lang/Class.class
        // jdk 11
        // jrt:/java.base/java/lang/Class.class
        // refer https://www.cnblogs.com/tankaixiong/p/14959796.html
    }
}
