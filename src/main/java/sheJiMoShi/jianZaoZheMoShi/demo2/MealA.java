package sheJiMoShi.jianZaoZheMoShi.demo2;

import sheJiMoShi.jianZaoZheMoShi.demo2.Meal;
import sheJiMoShi.jianZaoZheMoShi.demo2.MealBuilder;

/*
 * ConcreteBuilder(具体建造者)
 * 实现抽象接口，构建和装配各个部件
 *A套餐
 * */
public class MealA extends MealBuilder {

    @Override
    public void buildFood() {
        meal.setFood("炸鸡腿");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("冰镇啤酒");
    }
}
