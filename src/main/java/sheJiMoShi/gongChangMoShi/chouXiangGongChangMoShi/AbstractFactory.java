package sheJiMoShi.gongChangMoShi.chouXiangGongChangMoShi;

//3.为color和shape对象创建获取工厂的抽象类
public abstract class AbstractFactory {
    //抽象的方法不能有方法体
    public abstract Color getColor(String color);

    public abstract Shape getShape(String shape);
}
