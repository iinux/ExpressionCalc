package com.mycompany.helloworld.springboot;

import com.mycompany.helloworld.sensitive.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SmartRegisterListener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(@NotNull Class<? extends ApplicationEvent> aClass) {
        System.out.println("check supportsEventType " + aClass);
        // 只有UserRegisterEvent监听类型才会执行下面逻辑
        return aClass == UserRegisterEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        System.out.println("check supportsSourceType " + aClass);
        // 可以指定在某个类里发布UserRegisterEvent事件才执行
        return true;
    }

    @Override
    public void onApplicationEvent(@NotNull ApplicationEvent applicationEvent) {
        //转换事件类型
        UserRegisterEvent userRegisterEvent = (UserRegisterEvent) applicationEvent;
        //获取注册用户对象信息
        User user = userRegisterEvent.getUser();
        System.out.println("SmartRegisterListener" + user.getUsername());
    }

    /**
     * return 的数值越小证明优先级越高，执行顺序越靠前。
     */
    @Override
    public int getOrder() {
        return 10;
    }
}
