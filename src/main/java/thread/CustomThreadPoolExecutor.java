package thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//阻塞线程池
public class CustomThreadPoolExecutor {

    private ThreadPoolExecutor pool = null;

    /*
     * 线程池初始化方法
     *
     * corePoolSize  核心线程池大小--10
     * maximumPoolSize   最大线程池大小--30
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间--30+单位TimeUnit
     * TimeUnit  keepAliveTime时间单位--TimeUnit.MINUTES
     * workQueue 阻塞队列--new ArraryBlockingQueue<Runnable>(10)---10容量的阻塞队列
     * threadFactory 新建线程工厂--new CustomThreadFactory()--定制的线程工厂
     * rejectedExecutionHandler  当提交任务数超过maxmumpoolSize+workQueue只和时，即当提交第41个任务时（前面线程都没有执行完
     * ，次测试方法中用sleep(100)），任务会交给RejectedExecutionHandler来处理
     * */

    public void init() {
        pool = new ThreadPoolExecutor(10,
                30,
                30,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(10),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
    }

    public void destory() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    public ExecutorService getCumstomThreadPoolExecutor() {
        return this.pool;
    }

    public class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String thredName = CustomThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);
            System.out.println(thredName);
            t.setName(thredName);
            return t;
        }
    }

    public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                //核心构造点，由blockingqueue的offer改成put阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //测试构造的线程池
    public static void main(String[] args) {
        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor();
        //1.初始化线程池
        executor.init();
        //2.得到线程池
        ExecutorService pool = executor.getCumstomThreadPoolExecutor();
        for (int i = 1; i < 100; i++) {
            System.out.println("提交第" + i + "个任务。");
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(">>>>>>task is running");
                 /*   try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            });
        }
        //2.销毁--此处不能销毁，因为任务没有提交执行完成，如果销毁线程池，任务也就无法执行了
        executor.destory();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
