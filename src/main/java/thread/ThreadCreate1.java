package thread;

public class ThreadCreate1 extends Thread {

    //1.创建默认名称的新的Thread对象，名称为thread-0,1,2....
    public ThreadCreate1() {
        super();
    }

    //创建名称为name的thread对象
    public ThreadCreate1(String name) {
        super(name);
    }

    //重写run()方法
    @Override
    public void run() {
        try {
            //如果使用类名调用该方法，则改代码所在的类会休眠
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我们的线程名是:" + super.getName());
    }

    public static void main(String[] args) {
//        ThreadCreate1 threadTest = new ThreadCreate1();
//        threadTest.start();
//        new ThreadCreate1().start();
        ThreadCreate1 thread2 = new ThreadCreate1("哼哼");
        thread2.start();
        //得到线程的对象
        String name1 = thread2.getName();
        System.out.println("run方法线程的名称为"+name1);
        //返回当前正在执行的main线程的对象
        Thread thread = Thread.currentThread();
        //得到当前线程的名称
        thread.setName("旺旺");
        String name = thread.getName();
        System.out.println("得到当前线程的名称为："+name);
    }
}
