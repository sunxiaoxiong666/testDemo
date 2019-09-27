package sheJiMoShi.jianZaoZheMoShi.demo2;

//测试类（客户端类）
public class Test {

    public static void main(String[] args) {

        //套餐A
        MealA mealA = new MealA();
        //准备套餐A的服务员
        KfcWaiter kfcWaiter = new KfcWaiter(mealA);

        //获得套餐
        Meal meal = kfcWaiter.pre();
        System.out.println("套餐A有：" + meal.getFood() + ":" + meal.getDrink());
    }
}
