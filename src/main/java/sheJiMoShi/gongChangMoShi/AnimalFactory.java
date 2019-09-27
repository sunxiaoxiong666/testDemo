package sheJiMoShi.gongChangMoShi;

/*
    普通工厂模式
 * 3.创建生成“动物”对象的工厂
 * */
public class AnimalFactory {

    //使用getAnimal方法获得对象
    public Animal getAnimal(String animal) {
        if (animal == null) {
            return null;
        }
        if ("dog".equalsIgnoreCase(animal)) {
            return new Dog();
        } else if ("cat".equalsIgnoreCase(animal)) {
            return new Cat();
        } else if ("pig".equalsIgnoreCase(animal)) {
            return new Pig();
        }
        return null;
    }
}
