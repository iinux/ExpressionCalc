package cn.iinux.java.alpha.basic;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SystemClock {
    /**
     * 时钟更新间隔，单位毫秒
     */
    private final long period;

    /**
     * 现在时刻的毫秒数
     */
    private volatile long now;

    /**
     * 构造
     *
     * @param period 时钟更新间隔，单位毫秒
     */
    public SystemClock(long period) {
        this.period = period;
        this.now = System.currentTimeMillis();
        scheduleClockUpdating();
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "System Clock");
            thread.setDaemon(true);
            return thread;
        });
        scheduler.scheduleAtFixedRate(() -> now = System.currentTimeMillis(), period, period, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return now;
    }

    private static class InstanceHolder {
        public static final SystemClock INSTANCE = new SystemClock(1);
    }

    public static long now() {
        return InstanceHolder.INSTANCE.currentTimeMillis();
    }

    public static String nowDate() {
        return new Timestamp(InstanceHolder.INSTANCE.currentTimeMillis()).toString();
    }
}
