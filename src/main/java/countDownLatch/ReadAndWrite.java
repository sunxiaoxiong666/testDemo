package countDownLatch;

import java.sql.Timestamp;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ReadAndWrite {

    private final static CountDownLatch cdl = new CountDownLatch(3);
    private final static Vector v = new Vector();
    //使用线程池
    public final static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 15, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public static class WriteThread extends Thread {
        private final String writeThreadName;
        private final int stopTime;
        private final String str;

        public WriteThread(String name, int time, String str) {
            this.writeThreadName = name;
            this.stopTime = time;
            this.str = str;
        }

        @Override
        public void run() {
            System.out.println(writeThreadName + "开始写入工作");
            try {
                Thread.sleep(stopTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cdl.countDown();
            v.add(str);
            System.out.println(writeThreadName + "写入内容为：" + str + "。写入工程结束。");
        }
    }

    public static class ReadThread extends Thread {
        @Override
        public void run() {
            System.out.println("读操作之前必须进行写操作");
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < v.size(); i++) {
                System.out.println("读取第" + (i + 1) + "条记录内容为+" + v.get(i));
            }
            System.out.println("读操作完成。");
        }
    }

    public static void main(String[] args) {
        Thread readThread = new ReadThread();
        pool.execute(readThread);
        String[] str = {"线程1的内容", "线程2的内容", "线程3的内容"};
        for (int i = 0; i < 3; i++) {
            Thread thread = new WriteThread("writeThread" + (i + 1), 1000 * (i + 1), str[i]);
            pool.execute(thread);
        }

        /*new ReadThread().start();
        new WriteThread("writeThread1",1000,"线程1写入的内容").start();
        new WriteThread("writeThread2",1000,"线程2写入的内容").start();
        new WriteThread("writeThread3",1000,"线程3写入的内容").start();*/

    }
}
