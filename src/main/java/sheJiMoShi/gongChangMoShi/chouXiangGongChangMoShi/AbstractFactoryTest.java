package sheJiMoShi.gongChangMoShi.chouXiangGongChangMoShi;

//6.使用FactoryProducer获取AbstractFactory,通过传递的信息获取实体类的对象。
public class AbstractFactoryTest {

    public static void main(String[] args) {

        //获取形状的工厂
        AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");
        //获取circle形状的对象
        Shape circle = shapeFactory.getShape("circle");
        //调用circle的方法
        circle.draw();
        Shape square = shapeFactory.getShape("square");
        square.draw();

        AbstractFactory colorFactory = FactoryProducer.getFactory("color");
        Color red = colorFactory.getColor("red");
        red.fill();
        Color black = colorFactory.getColor("black");
        black.fill();
    }
}
