package sheJiMoShi.danLiMoShi.demo.jingTaiNeiBuLei;

/*
 * 登记式/静态内部类
 * 能达到双检锁模式一样的功能，
 * */
public class Singleton {

    public static class SingletonHolder {
        private static final Singleton singleton = new Singleton();
    }

    private Singleton() {
    }

    public static final Singleton getInstance() {
        return SingletonHolder.singleton;
    }
}
