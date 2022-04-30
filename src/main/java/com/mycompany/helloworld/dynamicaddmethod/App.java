package com.mycompany.helloworld.dynamicaddmethod;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class App {
    public static void main(String[] args) {
        try {
            String className = UserInfo.class.getName();
            System.out.println(className);
            UserInfo userInfo = new UserInfo();
            userInfo.setName("test");
            userInfo.setId(1);
            System.out.println("before:" + userInfo);

            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(className);
            CtMethod ctMethod = CtNewMethod.make("public String test() { return \"test() is called \"+ toString();  }", cc);
            cc.addMethod(ctMethod);

            AppClassLoader appClassLoader = AppClassLoader.getInstance();
            Class<?> clazz = appClassLoader.findClassByBytes(className, cc.toBytecode());
            // clazz.getDeclaredConstructor().newInstance();
            Object obj = appClassLoader.getObj(clazz, userInfo);
            // obj = clazz.newInstance();
            System.out.println("after:" + obj);

            // 测试反射调用添加的方法
            System.out.println(obj.getClass().getDeclaredMethod("test").invoke(obj));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
