package thread;

public class ThreadAnQuan1 implements Runnable {

    //总票数
    int piao = 100;

    @Override
    public void run() {
        //循环卖票
        while (true) {
            //说明有票，可以卖
            //使用同步代码块解决线程安全问题
            synchronized (ThreadAnQuan1.class) {
                if (piao > 1) {
                    System.out.println(Thread.currentThread().getName() + "卖出了" + piao + "号票。");
                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    piao--;
                } else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        //创建一个piao对象，保证只要一个piao对象
        ThreadAnQuan1 paio = new ThreadAnQuan1();
        //创建三个线程，分别使用piao作为任务
        new Thread(paio, "窗口1").start();
        new Thread(paio, "窗口2").start();
        new Thread(paio, "窗口3").start();
    }


}
