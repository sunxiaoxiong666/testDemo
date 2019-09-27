package sheJiMoShi.jianZaoZheMoShi.inter;

//创建一个表示食物条目的接口
public interface Item {

    //食物的名字
    public String name();

    //食物的包装
    public Packing packing();

    //食物的价格
    public float price();

}
