package thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//创建定长线程池，定时执行任务
public class ScheduledThreadPoolTest1 {
    public static void main(String[] args) {
        //创建线程池，
        ScheduledExecutorService POOL = Executors.newScheduledThreadPool(5);
        //延迟一秒执行
        POOL.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟1秒执行。");
            }
        }, 1, TimeUnit.SECONDS);
    }
}
