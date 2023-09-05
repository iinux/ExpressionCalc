package com.mycompany.helloworld.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class AviatorStudy {
    public static void main(String[] args) {

        String expression = "(W9 > 0) &&  (string.startsWith(phone,'171')) &&  (product == 4)";
        Map<String, Object> params = new HashMap<>();
        params.put("W9", 1);
        params.put("phone", "17112345678");
        // params.put("product", 4);
        Object ret = AviatorEvaluator.execute(expression, params, true);
        System.out.println(ret);

        // exec执行方式，无需传递Map格式
        String age = "18";
        System.out.println(AviatorEvaluator.exec("'His age is '+ age +'!'", age));

        // 注册自定义函数
        AviatorEvaluator.addFunction(new MultiplyFunction());
        // 方式1
        System.out.println(AviatorEvaluator.execute("multiply(12.23, -2.3)"));
        // 方式2
        Map<String, Object> params2 = new HashMap<>();
        params2.put("a", 12.23);
        params2.put("b", -2.3);
        System.out.println(AviatorEvaluator.execute("multiply(a, b)", params2));

        expression = "a+(b-c)>100";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression);

        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);

        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);

        final List<String> list = new ArrayList<>();
        list.add("hello");
        list.add(" world");

        final int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;

        final Map<String, Date> map = new HashMap<>();
        map.put("date", new Date());

        Map<String, Object> env2 = new HashMap<>();
        env2.put("list", list);
        env2.put("array", array);
        env2.put("map", map);

        System.out.println(AviatorEvaluator.execute(
                "list[0]+':'+array[0]+':'+'today is '+map.date", env2));

        Map<String, Object> env3 = new HashMap<String, Object>();
        env3.put("a", -5);
        String result2 = (String) AviatorEvaluator.execute("a>0? 'yes':'no'", env3);
        System.out.println(result2);

        String email = "hello2018@gmail.com";
        Map<String, Object> env4 = new HashMap<String, Object>();
        env4.put("email", email);
        String username = (String) AviatorEvaluator.execute("email=~/([\\w0-8]+)@\\w+[\\.\\w+]+/ ? $1 : 'unknow' ", env4);
        System.out.println(username);

        User user = new User(1,"jack","18");
        Map<String, Object> env5 = new HashMap<>();
        env5.put("user", user);

        String result3 = (String) AviatorEvaluator.execute(" '[user id='+ user.id + ',name='+user.name + ',age=' +user.age +']' ", env5);
        System.out.println(result3);
    }

    @Test
    public void test() {
        AviatorEvaluator.execute("nil == nil");  //true
        AviatorEvaluator.execute(" 3> nil");    //true
        AviatorEvaluator.execute(" true!= nil");    //true
        AviatorEvaluator.execute(" ' '>nil ");  //true
        AviatorEvaluator.execute(" a==nil ");   //true,a is null

        Map<String, Object> env = new HashMap<String, Object>();
        final Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(date);
        env.put("date", date);
        env.put("dateStr", dateStr);

        Boolean result = (Boolean) AviatorEvaluator.execute("date==dateStr", env);
        System.out.println(result);

        result = (Boolean) AviatorEvaluator.execute("date > '2009-12-20 00:00:00:00' ", env);
        System.out.println(result);

        result = (Boolean) AviatorEvaluator.execute("date < '2200-12-20 00:00:00:00' ", env);
        System.out.println(result);

        result = (Boolean) AviatorEvaluator.execute("date ==date ", env);
        System.out.println(result);

    }
}

class MultiplyFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {

        double num1 = FunctionUtils.getNumberValue(arg1, env).doubleValue();
        double num2 = FunctionUtils.getNumberValue(arg2, env).doubleValue();
        return new AviatorDouble(num1 * num2);
    }

    @Override
    public String getName() {
        return "multiply";
    }

}

