package sheJiMoShi.guanChaZheMoShi;

import java.util.Vector;

//3.定义具体的被观察者--杯子
public class Cup implements Observable {

    //被观察者维护的一个观察者对象列表
    private Vector<Observer> vector = new Vector<>();

    private float price;

    public Cup(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
        //修改价格是通知观察者
        notifyObserver();
    }

    //注册观察者
    @Override
    public void regisObserver(Observer observer) {
        vector.add(observer);
    }

    //移除观察者
    @Override
    public void removeObserver(Observer observer) {
        vector.remove(observer);
    }

    //通知所有的观察者对象变化价格
    @Override
    public void notifyObserver() {
        for (Observer observer : vector) {
            observer.update(price);
        }
    }
}
