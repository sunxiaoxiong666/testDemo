package sheJiMoShi.jianZaoZheMoShi;

import sheJiMoShi.jianZaoZheMoShi.entity.Meal;
import sheJiMoShi.jianZaoZheMoShi.entity.MealBuilder;

public class Test {
    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();
        Meal vegMeal = mealBuilder.getVegBurger();
        System.out.println("veg meal");
        vegMeal.showItems();
        System.out.println("total coat:" + vegMeal.getCost());

        Meal nonVegMeal = mealBuilder.getNonVegBurger();
        System.out.println("\n\nnon veg meal");
        nonVegMeal.showItems();
        System.out.println("total cost:" + nonVegMeal.getCost());
    }
}
