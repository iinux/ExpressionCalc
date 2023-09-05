package cn.iinux.java.alpha.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// -XX:MaxMetaspaceSize=10m

public class MethodSpaceOverflowTest {
    public static void main(String[] args) throws InterruptedException {
        for (;;) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MethodSpaceOverflowTest.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, objects);
                }
            });
            System.out.println("hello world");
            enhancer.create();
            Thread.sleep(1000);
        }
    }
}
