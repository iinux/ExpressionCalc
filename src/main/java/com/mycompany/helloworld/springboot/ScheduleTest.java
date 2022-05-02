package com.mycompany.helloworld.springboot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTest {

    // @Scheduled(cron = "* * * * * ?")
    @ScheduleAnnotation(expireTime = 10)
    public void scheduleMethod() {
        System.out.println("run");
    }
}
