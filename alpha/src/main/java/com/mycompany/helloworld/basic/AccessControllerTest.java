package com.mycompany.helloworld.basic;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class AccessControllerTest {
    public static void main(String[] args) throws Exception {
        System.out.println(System.getSecurityManager());

        AccessControllerTest test = new AccessControllerTest();
        test.fileRead();

        System.out.println("end");
    }

    @Test
    public void doPrivileged() {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                System.out.println("in run method");
                return new Object();
            }
        });
    }

    @Test
    public void fileReadCheck() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        System.getSecurityManager().checkRead("foo.txt");
    }

    @Test
    public void fileRead() throws Exception {
        if (System.getSecurityManager() == null) {
            System.out.println("set security manager");
            System.setSecurityManager(new SecurityManager());
        }

        System.out.println(System.getSecurityManager());
        FileReader reader = new FileReader();
        reader.read("test/foo.txt");
    }

    static class FileReader {
        public void read(String fileName) throws Exception {
            System.out.println(fileName);
            InputStream in = new FileInputStream(fileName);
        }
    }
}

