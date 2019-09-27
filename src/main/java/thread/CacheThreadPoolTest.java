package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//可缓存线程池
public class CacheThreadPoolTest {

    public static void main(String[] args) {
        //创建一个可缓存线程池,线程池无限大
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            try {
                //sleep是为了看到是一个线程执行，当池子中有空闲的线程，就会创建了
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行。");
                }
            });
        }
        //关闭线程池
        executorService.shutdown();
    }
}
