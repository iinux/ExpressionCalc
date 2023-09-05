package com.mycompany.helloworld.basic;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SpringResourceModify {
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // addClassPath();
        read();
        // write();
    }

    static void addClassPath() throws NoSuchMethodException, MalformedURLException, InvocationTargetException, IllegalAccessException {
        File programRootDir = new File("./");
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
        add.setAccessible(true);
        add.invoke(classLoader, programRootDir.toURI().toURL());
    }

    static void read() throws IOException {
        InputStream stream = null;
        BufferedReader bufferedReader = null;
        List<String> list = new ArrayList<>();
        try {
            stream = SpringResourceModify.class.getClassLoader().getResourceAsStream("sensitiveWords2.dat");
            if (stream == null) {
                System.out.println("stream is null");
                return;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String txt;
            while ((txt = bufferedReader.readLine()) != null) {
                list.add(txt);
            }
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (stream != null) {
                stream.close();
            }
        }
    }

    static void write() throws IOException {
        String filePath = new ClassPathResource("sensitiveWords.dat").getPath();
        BufferedWriter sensitiveWordsWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath)));
        String newSensitiveWords = "def";
        sensitiveWordsWriter.write(newSensitiveWords);
        sensitiveWordsWriter.flush();
        sensitiveWordsWriter.close();
    }
}
