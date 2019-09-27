package sheJiMoShi.jianZaoZheMoShi.demo2;

/*
 * Builder(抽象建造者)
 * 2.创建一个product对象的各个部件指定的抽象接口。
 * */
public abstract class MealBuilder {

    Meal meal = new Meal();

    public abstract void buildFood();

    public abstract void buildDrink();

    public Meal getMeal() {
        return meal;
    }
}
