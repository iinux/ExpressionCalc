package com.mycompany.helloworld.springboot;

import com.mycompany.helloworld.sensitive.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Autowired
    private ApplicationContext appContext;

    @Async
    public void asyncNewUser() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appContext.publishEvent(new UserRegisterEvent(this, new User()));
    }
}
