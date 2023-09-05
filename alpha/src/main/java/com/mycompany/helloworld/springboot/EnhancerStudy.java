package com.mycompany.helloworld.springboot;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
// import jdk.internal.org.objectweb.asm.Type;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

@AllArgsConstructor
public class EnhancerStudy {
    static class Car {
        void print() {
            System.out.println("I am a car");
        }

        final void printFinal() {
            System.out.println("I am a final car");
        }

        String returnString() {
            return "I am a return string car";
        }
    }

    // https://www.cnblogs.com/micrari/p/7565632.html
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, System.getProperty("user.dir") + "/cglibclass");
    }

    @Test
    public void modifyMethod() {
        Car car = new Car();
        car.print();
        try {
            ClassPool pool = new ClassPool(true);
            CtClass ct = pool.getCtClass(car.getClass().getName());// 加载这个类
            // 获取被修改的方法
            CtMethod m = ct.getDeclaredMethod("print");
            m.setBody("System.out.println(\"I am a fake car\");"); // 直接修改方法体
            // 转为class
            ct.toClass();
            // 释放对象
            ct.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
        car.print();
    }

    @Test
    public void case1() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy)
                    throws Throwable {
                System.out.println("before");
                Object res = methodProxy.invokeSuper(obj, args);
                System.out.println("after");
                // System.out.println(Type.getMethodDescriptor(method));
                return res;
            }
        });
        Car car = (Car) enhancer.create();
        car.print();
        car.printFinal();
    }

    @Test
    public void case2() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(new LazyLoader() {
            @Override
            public Object loadObject() {
                System.out.println("prepare loading");
                Car car = new Car();
                System.out.println("after loading");
                System.out.println(car);
                return car;
            }
        });
        Car car = (Car) enhancer.create();
        System.out.println(car);
        car.print();
        car.print();
    }

    @Test
    public void case3() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(new Dispatcher() {
            @Override
            public Object loadObject() throws Exception {
                System.out.println("prepare loading");
                Car car = new Car();
                System.out.println("after loading");
                System.out.println(car);
                return car;
            }
        });
        Car car = (Car) enhancer.create();
        System.out.println(car);
        car.print();
        car.print();
    }

    @Test
    public void case4() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
                if (method.getReturnType() == void.class) {
                    System.out.println("hack");
                }
                return null;
            }
        });
        Car car = (Car) enhancer.create();
        car.returnString();
        car.print();
    }

    @Test
    public void case5() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "hack!";
            }
        });

        Car car = (Car) enhancer.create();
        System.out.println(car.returnString());
    }

    @Test
    public void case6() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        CallbackHelper helper = new CallbackHelper(Car.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                if (method.getReturnType() == void.class) {
                    return new MethodInterceptor() {
                        @Override
                        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy)
                                throws Throwable {
                            System.out.println("before invocation");
                            Object res = methodProxy.invokeSuper(obj, args);
                            System.out.println("after invocation");
                            return res;
                        }
                    };
                } else if (method.getReturnType() == String.class) {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return "a hacked car";
                        }
                    };
                } else return NoOp.INSTANCE;
            }
        };

        enhancer.setCallbacks(helper.getCallbacks());
        enhancer.setCallbackFilter(helper);

        Car car = (Car) enhancer.create();
        car.print();
        System.out.println(car.returnString());
    }

    @Test
    public void case7() {
        HelloWorld hello = new HelloWorld();
        Enhancer enhancer = new Enhancer();
        //设置代理目标
        enhancer.setSuperclass(hello.getClass());
        //设置调用过滤器
        CallbackFilter filter = new CallBackFilterTest();
        enhancer.setCallbackFilter(filter);
        //创建各个目标代理方法的回调，回调的顺序要与过滤器索引一致
        Callback[] callbacks = new Callback[]{new MethodInterceptor() {
            /**
             *
             * 方法描述 当对基于代理的方法回调时，在调用原方法之前会调用该方法
             * 拦截对目标方法的调用
             *
             * @param obj 代理对象
             * @param method 拦截的方法
             * @param args 拦截的方法的参数
             * @param proxy 代理
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object obj, Method method, Object[] args,
                                    MethodProxy proxy) throws Throwable {
                Object result = null;
                try {
                    // 前置通知
                    before();
                    result = proxy.invokeSuper(obj, args);
                    // 后置通知
                    after();
                } catch (Exception e) {
                    // 异常通知
                    exception();
                } finally {
                    // 方法返回前通知
                    beforeReturning();
                }

                return result;
            }

            private void before() {
                System.out.println("before method invoke...");
            }

            private void after() {
                System.out.println("after method invoke...");
            }

            private void exception() {
                System.out.println("exception method invoke...");
            }

            private void beforeReturning() {
                System.out.println("beforeReturning method invoke...");
            }
        }, NoOp.INSTANCE};
        // 设置单一回调对象，在调用中拦截对目标方法的调用
        // enhancer.setCallback(NoOp.INSTANCE);
        enhancer.setCallbacks(callbacks);
        // 设置类加载器
        enhancer.setClassLoader(hello.getClass().getClassLoader());

        HelloWorld obj = (HelloWorld) enhancer.create();

        System.out.println(obj.update());
        System.out.println("=============");
        System.out.println(obj.save());
    }

    public static class HelloWorld {
        public String save() {
            System.out.println("save...");
            return "save";
        }

        public String update() {
            System.out.println("update...");
            return "update";
        }
    }

    public static class CallBackFilterTest implements CallbackFilter {
        /**
         * 方法返回的值是和callback回调接口数组一一对应的数组下标
         */
        @Override
        public int accept(Method method) {
            String name = method.getName();
            if ("save".equals(name)) {
                return 0;
            }
            return 1;
        }

    }
}
