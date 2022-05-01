package com.mycompany.helloworld.log4j;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class ParameterizedMessageTest {
    public static void main(String[] args) {
        JSONObject returnObject = new JSONObject();
        returnObject.put("a", "b");

        ParameterizedMessage p = new ParameterizedMessage("msg={}", returnObject);
        StringBuilder stringBuilder = new StringBuilder();
        p.formatTo(stringBuilder);

        System.out.println(stringBuilder);
        System.out.println(returnObject);
    }
}
