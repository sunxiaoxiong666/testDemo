package sheJiMoShi.jianZaoZheMoShi.impl;

import sheJiMoShi.jianZaoZheMoShi.inter.Item;
import sheJiMoShi.jianZaoZheMoShi.inter.Packing;


public abstract class ColdDrink implements Item {
    @Override
    public Packing packing() {
        return new Bottle();
    }
}
