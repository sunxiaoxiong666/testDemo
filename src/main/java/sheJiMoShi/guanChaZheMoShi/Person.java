package sheJiMoShi.guanChaZheMoShi;

//4.定义具体的观察者对象
public class Person implements Observer {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public void update(float price) {
        System.out.println(name + "关注的杯子价格更新为：" + price);
    }
}
