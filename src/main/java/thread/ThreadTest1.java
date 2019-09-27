package thread;

//匿名内部类创建线程
public class ThreadTest1 {
    public static void main(String[] args) {
        //1.使用Thread类作为父类，创建的匿名对象属于线程对象
       /* Thread thread=new Thread(){
            @Override
            public void run() {
                System.out.println("没有名字的Thread子类:"+super.getName());
            }
        };
        thread.setName("小花");
        thread.start();*/

        //2.使用Runnable作为父类
      /*  Runnable runnable=new Runnable() {
            @Override
            public void run() {
                //获取线程名
                String name = Thread.currentThread().getName();
                System.out.println("无名字的子类线程:"+name);
            }
        };

        //创建一个线程,并绑定任务runnable，开启线程
        new Thread(runnable).start();*/

        //3.一行代码
        new Thread() {
            @Override
            public void run() {
                System.out.println("一行代码得到线程名字:" + super.getName());
            }
        }.start();

        //4.
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("一行：" + Thread.currentThread().getName());
            }
        }).start();
    }
}
