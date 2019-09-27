package sheJiMoShi.gongChangMoShi.chouXiangGongChangMoShi;

//创建扩展了Abstrractory的工厂类，根据给的信息生成实体类的对象
public class ShapeFactpory extends AbstractFactory {
    @Override
    public Shape getShape(String shape) {

        //生成形状的对象
        if ("square".equalsIgnoreCase(shape)) {
            return new Square();
        } else if ("circle".equalsIgnoreCase(shape)) {
            return new Circle();
        } else {
            return null;
        }
    }

    @Override
    public Color getColor(String color) {
        return null;
    }
}
