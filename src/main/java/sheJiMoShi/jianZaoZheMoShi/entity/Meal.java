package sheJiMoShi.jianZaoZheMoShi.entity;

import sheJiMoShi.jianZaoZheMoShi.inter.Item;

import java.util.ArrayList;
import java.util.List;

//创建一个meal类，带有item的4个实体类
public class Meal {
    private List<Item> items = new ArrayList<Item>();

    public void addItem(Item item) {
        items.add(item);
    }

    public float getCost() {
        float cost = 0.0f;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    public void showItems() {
        for (Item item : items) {
            System.out.print("Item:" + item.name());
            System.out.print(",packing:" + item.packing().pack());
            System.out.println(",price:" + item.price());
        }
    }
}
