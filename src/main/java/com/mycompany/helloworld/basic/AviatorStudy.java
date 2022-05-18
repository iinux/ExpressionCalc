package com.mycompany.helloworld.basic;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

public class AviatorStudy {
    public static void main(String[] args) {

        String expression = "(W9 > 0) &&  (string.startsWith(phone,'171')) &&  (product == 4)";
        Map<String, Object> params = new HashMap<>();
        params.put("W9", 1);
        params.put("phone", "17112345678");
        // params.put("product", 4);
        Object ret = AviatorEvaluator.execute(expression, params, true);
        System.out.println(ret);
    }
}
