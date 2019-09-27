package sheJiMoShi.guanChaZheMoShi;

public class Test {
    public static void main(String[] args) {
        //创建一个被观察者对象
        Cup cup = new Cup(2);
        //创建两个观察者对象
        Person person = new Person("小明");
        Person person2 = new Person("小花");
        //注册成为一个观察者
        cup.regisObserver(person);
        cup.regisObserver(person2);
        System.out.println("第一轮涨价的价格：");
        cup.setPrice(3);
        System.out.println("第二轮涨价的价格：");
        cup.setPrice(4);
        //移除小花
        cup.removeObserver(person2);
        System.out.println("第三轮涨价的价格：");
        cup.setPrice(5);


    }
}
