package cn.iinux.java.alpha.mtasm.bytebuddystudy;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class LoggingInterceptor {
    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        System.out.println("Before method: " + method.getName());
        try {
            return callable.call();
        } finally {
            System.out.println("After method: " + method.getName());
        }
    }
}
