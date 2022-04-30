package com.mycompany.helloworld;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Test;

public class Basic {
    @Test
    public void assignableFrom() {
        System.out.println(this.getClass().isAssignableFrom(Object.class));
        System.out.println(Object.class.isAssignableFrom(this.getClass()));
    }

    @Test
    public void stackTrace() {
        String trace = ExceptionUtils.getStackTrace(new Exception());
        System.out.println(trace);

    }
}
