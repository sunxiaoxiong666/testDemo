package sheJiMoShi.gongChangMoShi;

/*
 * 静态工厂方法模式(最常用)
 * */
public class AnimalStaticFactory {

    //静态方法得到对象
    public static Animal getDog() {
        return new Dog();
    }

    public static Animal getCat() {
        return new Cat();
    }

    public static Animal getPig() {
        return new Pig();
    }
}
