package cn.iinux.java.alpha.basic;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("find class");
        return super.findClass(name);
    }

    public Class<?> loadClass(String name) {
        System.out.println("MyClassLoader loadClass " + name);
        try {
            String url = "file:A.class";
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while (data != -1) {
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass(name, classData, 0, classData.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> ac = myClassLoader.loadClass("java.lang.Objectx");
        System.out.println(ac);
    }

    @Test
    public void testURLClassLoader() throws MalformedURLException, ClassNotFoundException {
        String path = "file:" + System.getProperty("user.dir") + "/";
        URL url = new URL(path);
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> classA = classLoader.loadClass("A");
        System.out.println(classA);

    }
}
