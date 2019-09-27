package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//单线程的线程池
public class SingleThreadPoolTest {
    public static void main(String[] args) {
        //创建线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int j = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行，打印的是：" + j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //关闭线程池
        pool.shutdown();
    }
}
