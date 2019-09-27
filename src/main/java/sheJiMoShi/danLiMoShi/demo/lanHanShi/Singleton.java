package sheJiMoShi.danLiMoShi.demo.lanHanShi;

/*
懒汉式
优点：第一次调用时才会初始化生成对象，避免浪费内存资源
缺点：必须加synchronized锁才能保证是单例，但是加锁会影响效率
*/
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
