package sheJiMoShi.gongChangMoShi;

//使用工厂获取对象
public class AnimalTest {
    public static void main(String[] args) {
        //生成工厂
        AnimalFactory animalFactory = new AnimalFactory();
        //传入参数，获取dog对象
        Animal dog = animalFactory.getAnimal("dog");
        //调用对象的方法
        dog.name();

        Animal cat = animalFactory.getAnimal("cat");
        cat.name();

        Animal pig = animalFactory.getAnimal("pig");
        pig.name();

        //静态工厂方法模式,static修饰的静态方法中可以直接调用另一个类中的静态方法
        Animal dog1 = AnimalStaticFactory.getDog();
        dog1.name();
    }
}
