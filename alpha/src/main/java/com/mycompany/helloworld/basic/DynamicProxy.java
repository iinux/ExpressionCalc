package com.mycompany.helloworld.basic;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {
    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy();
        dynamicProxy.testDynamicProxy();
    }

    @Test
    public void testDynamicProxy() {
        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // boolean saveGeneratedFiles = AccessController.doPrivileged(new GetBooleanAction("sun.misc.ProxyGenerator.saveGeneratedFiles"));
        // System.out.println(saveGeneratedFiles);
        IUserDao target = new UserDao();
        System.out.println(target.getClass());  //输出目标对象信息
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());  //输出代理对象信息
        System.out.println(proxy.save());  //执行代理方法
    }

    public interface IUserDao {
        int save();
    }

    public static class UserDao implements IUserDao {

        @Override
        public int save() {
            System.out.println("保存数据");
            return 1;
        }
    }

    public static class ProxyFactory {

        private final Object target;// 维护一个目标对象

        public ProxyFactory(Object target) {
            this.target = target;
        }

        // 为目标对象生成代理对象
        public Object getProxyInstance() {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                    new InvocationHandler() {

                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("开启事务");

                            // 执行目标对象方法
                            Object returnValue = method.invoke(target, args);

                            System.out.println("提交事务");
                            return returnValue;
                        }
                    });
        }
    }
}
