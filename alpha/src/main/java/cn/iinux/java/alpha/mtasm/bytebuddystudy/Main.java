package cn.iinux.java.alpha.mtasm.bytebuddystudy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class Main {
    public static void main(String[] args) throws Exception {
        UserService userService = new ByteBuddy()
                .subclass(UserService.class)
                .method(ElementMatchers.named("createUser"))
                .intercept(MethodDelegation.to(LoggingInterceptor.class))
                .make()
                .load(Main.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();

        userService.createUser("Alice");
    }
}
