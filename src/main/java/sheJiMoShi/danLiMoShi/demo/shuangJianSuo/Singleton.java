package sheJiMoShi.danLiMoShi.demo.shuangJianSuo;

/*
 * 双检锁
 * 采用双锁机制，能在线程安全的情况下保持高性能。
 * volatile（java5）：可以保证多线程下的可见性;
读volatile：每当子线程某一语句要用到volatile变量时，都会从主线程重新拷贝一份，这样就保证子线程的会跟主线程的一致。
写volatile: 每当子线程某一语句要写volatile变量时，都会在读完后同步到主线程去，这样就保证主线程的变量及时更新。
 * */
public class Singleton {
    private volatile static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    return new Singleton();
                }
            }
        }
        return null;
    }
}
