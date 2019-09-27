package sheJiMoShi.gongChangMoShi.chouXiangGongChangMoShi;

//5.创建一个工厂创造器/生成器类，通过传递形状或颜色信息来获取工厂。
public class FactoryProducer {
    public static AbstractFactory getFactory(String str) {
        //生成工厂
        if ("shape".equalsIgnoreCase(str)) {
            return new ShapeFactpory();
        } else if ("color".equalsIgnoreCase(str)) {
            return new ColorFactory();
        }
        return null;
    }
}
