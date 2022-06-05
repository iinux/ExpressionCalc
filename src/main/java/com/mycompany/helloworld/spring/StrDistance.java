package com.mycompany.helloworld.spring;

import org.springframework.beans.PropertyMatches;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class StrDistance {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
        // System.out.println(mainArgs);

        int distance = 0;
        // distance = PropertyMatches.calculateStringDistance("hello", "hellp");

        Class<?>[] argTypes = new Class[] {String.class, String.class};
        Method m = PropertyMatches.class.getDeclaredMethod("calculateStringDistance", argTypes);
        m.setAccessible(true); //if security settings allow this
        Object o = m.invoke(null, "hello", "hellp"); //use null if the method is static

        System.out.println(o);
    }
}
