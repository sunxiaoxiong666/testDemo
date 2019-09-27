package sheJiMoShi.jianZaoZheMoShi.demo2;

/*
 * B套餐
 * */
public class MeaB extends MealBuilder {

    @Override
    public void buildFood() {
        meal.setFood("鸭脖");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("可乐");
    }
}
