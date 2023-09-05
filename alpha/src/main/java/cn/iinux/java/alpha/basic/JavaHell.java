package cn.iinux.java.alpha.basic;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class JavaHell {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String resourceName = "java/lang/String.class";

        Enumeration<URL> urls = classLoader.getResources(resourceName);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url);
        }
    }
}
