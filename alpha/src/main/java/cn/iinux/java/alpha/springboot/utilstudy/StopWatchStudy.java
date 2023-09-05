package cn.iinux.java.alpha.springboot.utilstudy;

import org.junit.Test;
import org.springframework.util.StopWatch;

public class StopWatchStudy {
    @Test
    public void run() {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Thread.sleep(1000);
            stopWatch.stop();
            System.out.println(stopWatch.getTotalTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
