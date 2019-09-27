package sheJiMoShi.jianZaoZheMoShi.entity;

import sheJiMoShi.jianZaoZheMoShi.impl.ColdDrink;

public class Pepsi extends ColdDrink {
    @Override
    public String name() {
        return "pepsi";
    }

    @Override
    public float price() {
        return 10.0f;
    }
}
