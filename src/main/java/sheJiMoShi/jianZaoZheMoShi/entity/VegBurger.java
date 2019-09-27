package sheJiMoShi.jianZaoZheMoShi.entity;

import sheJiMoShi.jianZaoZheMoShi.impl.Burger;

//创建扩展了Burger的实体类
public class VegBurger extends Burger {
    @Override
    public String name() {
        return "ver burger";
    }

    @Override
    public float price() {
        return 25.0f;
    }
}
