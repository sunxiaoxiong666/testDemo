package sheJiMoShi.jianZaoZheMoShi.entity;

//创建MealBuilder类，用于创建Meal对象
public class MealBuilder {

    public Meal getVegBurger() {
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    public Meal getNonVegBurger() {
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
