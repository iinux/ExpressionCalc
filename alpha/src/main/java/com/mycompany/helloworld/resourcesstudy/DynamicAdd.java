package com.mycompany.helloworld.resourcesstudy;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;

public class DynamicAdd {
    public static void main(String[] args) throws Exception {
        URL res = DynamicAdd.class.getResource("/mybatis-config.xml");
        String content = IOUtils.toString(res, Charset.defaultCharset());
        System.out.println(content);

        setUp();

        res = DynamicAdd.class.getResource("/1.properties");
        content = IOUtils.toString(res, Charset.defaultCharset());
        System.out.println(content);
    }

    public static void setUp() throws Exception {
        File programRootDir = new File("input");
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
        add.setAccessible(true);
        add.invoke(classLoader, programRootDir.toURI().toURL());
    }
}
