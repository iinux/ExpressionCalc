package cn.iinux.java.alpha.springboot;

import cn.iinux.java.alpha.sensitive.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HelloService {
    @Autowired
    private ApplicationContext appContext;

    @Async
    public void asyncNewUser() throws Exception {
        try {
            log.info("start sleep");
            TimeUnit.SECONDS.sleep(10);
            throw new Exception();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appContext.publishEvent(new UserRegisterEvent(this, new User()));
    }
}
