package com.mycompany.helloworld.basic;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

class CalleeApp {

    // vm option: -Xbootclasspath/a:/home/qzhang/git/ExpressionCalc/target/classes

    @CallerSensitive
    public void call() {
        Class<?> clazz = Reflection.getCallerClass(2);
        Class<?> clazz1 = Reflection.getCallerClass();
        System.out.println(clazz);
        System.out.println(clazz1);
        System.out.println(getCallerClass());
    }

    public String getCallerClass() {
        // 通过堆栈信息获取调用当前方法的类名和方法名
        String className = "";
        String methodName = "";
        StackTraceElement[] elements = new Throwable().getStackTrace();
        for (int i = 0; i < elements.length; i++){
            if (this.getClass().getName().equals(elements[i].getClassName())){
                // 获取堆栈的下一个元素，就是调用者元素
                // 如果想要获取当前方法所在类的信息，直接读取elements[i]就可以了
                className = elements[i + 2].getClassName();
                methodName = elements[i + 2].getMethodName();
                break;
            }
        }
        return className + "." + methodName;
    }

}

public class CallerApp {

    public static void main(String[] args) {
        CalleeApp app = new CalleeApp();
        Caller1 c1 = new Caller1();
        c1.run(app);
    }

    static class Caller1 {
        void run(CalleeApp calleeApp) {
            if (calleeApp == null) {
                throw new IllegalArgumentException("callee can not be null");
            }
            calleeApp.call();
        }
    }

}
