package sheJiMoShi.gongChangMoShi.chouXiangGongChangMoShi;

//4.创建扩展了Abstrractory的工厂类，根据给的信息生成实体类的对象
public class ColorFactory extends AbstractFactory {
    //生成color对象
    @Override
    public Color getColor(String color) {
        if ("red".equalsIgnoreCase(color)) {
            return new Red();
        } else if ("black".equalsIgnoreCase(color)) {
            return new Black();
        }
        return null;
    }

    @Override
    public Shape getShape(String shape) {
        return null;
    }
}
