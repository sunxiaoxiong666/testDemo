package sheJiMoShi.jianZaoZheMoShi.entity;

import sheJiMoShi.jianZaoZheMoShi.impl.Burger;

public class ChickenBurger extends Burger {
    @Override
    public String name() {
        return "chicken burger";
    }

    @Override
    public float price() {
        return 50.0f;
    }
}
