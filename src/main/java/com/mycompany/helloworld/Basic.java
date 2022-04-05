package com.mycompany.helloworld;

import org.junit.Test;

public class Basic {
    @Test
    public void assignableFrom() {
        System.out.println(this.getClass().isAssignableFrom(Object.class));
        System.out.println(Object.class.isAssignableFrom(this.getClass()));
    }
}
