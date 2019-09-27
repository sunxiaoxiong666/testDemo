package sheJiMoShi.danLiMoShi.demo.eHanShi;


/*
 * 饿汉式
 * 优点:没有加锁，执行效率高
 * 缺点：类加载时就初始化生成对象，浪费内存
 * */
public class Singleton {

    private static Singleton singleton = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return singleton;
    }
}
