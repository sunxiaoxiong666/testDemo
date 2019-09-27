package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//固定个数的线程池
public class FixedThreadPoolTest {
    public static void main(String[] args) {
        //创建线程池
        int nThreads = 4;
        //设置线程池的大小可以根据系统资源设置
        int i1 = Runtime.getRuntime().availableProcessors();
        System.out.println(i1);
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < 10; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行。");
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
