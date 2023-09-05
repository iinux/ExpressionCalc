package cn.iinux.java.alpha.springboot;

import org.springframework.stereotype.Service;

@Service
public class ScheduleTest {

    // @Scheduled(cron = "* * * * * ?")
    @ScheduleAnnotation(expireTime = 10)
    public void scheduleMethod() {
        System.out.println("run");
    }
}
