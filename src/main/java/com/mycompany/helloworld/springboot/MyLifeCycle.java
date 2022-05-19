package com.mycompany.helloworld.springboot;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

@Component
public class MyLifeCycle implements Lifecycle {
    private volatile boolean running = false;

    @Override
    public void start() {
        System.out.println("my life cycle start");
        running = true;
    }

    @Override
    public void stop() {
        System.out.println("my life cycle stop");
        running = false;
    }

    @Override
    public boolean isRunning() {
        System.out.println("my life cycle isRunning " + running);
        return running;
    }
}
