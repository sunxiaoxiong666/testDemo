package mapReduce.order;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

//定义订单信息的实体类
public class OrderBean implements WritableComparable<OrderBean> {

    private int order_id;//订单id号
    private double price;//订单价格

    //2.反序列化时需要反射调用空参构造函数，所以必须有
    public OrderBean() {
        super();
    }

    //全参构造方法
    public OrderBean(int order_id, double price) {
        this.order_id = order_id;
        this.price = price;
    }


    //3.序列化方法
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(order_id);
        out.writeDouble(price);
    }

    //4.反序列化方法，顺序必须要与序列化方法一致
    @Override
    public void readFields(DataInput in) throws IOException {
        order_id = in.readInt();
        price = in.readDouble();
    }

    //5.编写tostring方法
    @Override
    public String toString() {
        return order_id + "\t" + price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //6.对比方法，二次排序
    @Override
    public int compareTo(OrderBean o) {
        int result = 0;
        //1.按照订单id排序
        if (order_id > o.getOrder_id()) {
            result = 1;
        } else if (order_id < o.getOrder_id()) {
            result = -1;
        } else {
            //2.价格倒叙排序
            result = price > o.getPrice() ? -1 : 1;
        }
        return result;
    }
}
