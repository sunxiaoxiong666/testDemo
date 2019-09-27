package thread;

/*
 * 创建线程的方式--实现Runnable接口
 * 要作为任务类，必须实现Runnable接口
 * */
public class ThreadCreate2 implements Runnable {

    //该方法是Runnable接口规定必须重写的方法，将来Thread类的对象自动调用该方法
    @Override
    public void run() {
        //获取线程对象
        Thread thread = Thread.currentThread();
        //获取线程名字
        String name = thread.getName();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("调用我们任务的线程是：" + name);
    }

    public static void main(String[] args) {
        //创建任务对象
        ThreadCreate2 threadCreate2 = new ThreadCreate2();
        //使用任务对象作为参数，创建一个线程
        Thread thread = new Thread(threadCreate2, "旺旺");
        //执行线程
//        thread.run();//单线程效果，执行顺序不会变，按顺序执行
        thread.start();//多线程效果，"旺旺"子线程和main线程同时执行
//        int i=1/0;//线程出错只会影响main线程，不会影响"旺旺"子线程
        System.out.println("main线程执行完");
    }
}
