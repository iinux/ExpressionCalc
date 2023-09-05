package cn.iinux.java.alpha.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyAdvisor2 implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("before MyAdvisor2 invoked");
        Object result = methodInvocation.proceed();
        System.out.println("after MyAdvisor2 invoked");
        return result;
    }
}
