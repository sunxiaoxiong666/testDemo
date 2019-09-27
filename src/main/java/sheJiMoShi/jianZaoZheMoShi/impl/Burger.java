package sheJiMoShi.jianZaoZheMoShi.impl;

import sheJiMoShi.jianZaoZheMoShi.inter.Item;
import sheJiMoShi.jianZaoZheMoShi.inter.Packing;

//创建实现item接口的抽象类，该类提供了默认的方法
public abstract class Burger implements Item {
    @Override
    public Packing packing() {
        return new Wrapper();
    }
}
