package sheJiMoShi.guanChaZheMoShi;

/*
 * 1.定义被观察者所具有的接口
 * */
public interface Observable {
    //注册为一个观察者
    public void regisObserver(Observer observer);

    //取消观察者
    public void removeObserver(Observer observer);

    //通知所有观察者更改信息
    public void notifyObserver();
}
