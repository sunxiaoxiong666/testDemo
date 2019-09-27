package sheJiMoShi.jianZaoZheMoShi.entity;

import sheJiMoShi.jianZaoZheMoShi.impl.ColdDrink;

public class Coke extends ColdDrink {
    @Override
    public String name() {
        return "coke";
    }

    @Override
    public float price() {
        return 5.0f;
    }
}
