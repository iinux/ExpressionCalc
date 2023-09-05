package com.mycompany.helloworld.basic;

import org.junit.Test;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ServiceLoader;

public class SqlDriver {
    public static void main(String[] args) {
        System.out.println(System.getProperty("jdbc.drivers"));
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = serviceLoader.iterator();

        while (iterator.hasNext()) {
            Driver driver = iterator.next();

            System.out.println("driver: " + driver.getClass() + ", loader: " + driver.getClass().getClassLoader());
        }

        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(ServiceLoader.class.getClassLoader());
    }

    @Test
    public void driverManager() throws ClassNotFoundException, SQLException {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytestdb", "username", "password");
    }
}
