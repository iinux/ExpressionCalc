package cn.iinux.java.alpha.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;

public class MyAdvisor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("before MyAdvisor invoked");
        Object result = methodInvocation.proceed();
        System.out.println("after MyAdvisor invoked");
        return result;
    }
}
