package thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//定长线程池，周期执行
public class ScheduledThreadPoolTest2 {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟一秒，每三秒执行一次。");
            }
        }, 1, 3, TimeUnit.SECONDS);
    }
}
