package com.mycompany.helloworld.basic;

import com.mycompany.helloworld.sensitive.User;
import org.apache.commons.lang3.SerializationUtils;

public class ObjClone {
    public static void main(String[] args) {
        User father = new User();
        father.setUsername("1");
        User father1 = SerializationUtils.clone(father);
        father1.setUsername("2");
        System.out.println(father.getUsername() + father1.getUsername());
    }
}
