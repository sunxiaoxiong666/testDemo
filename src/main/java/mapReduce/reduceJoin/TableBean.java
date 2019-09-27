package mapReduce.reduceJoin;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

//创建订单和产品合并后的bean类，因为要序列化所以是实现writable
public class TableBean implements Writable {

    private String order_id;//订单id
    private String p_id;//产品id
    private int amount;//产品数量
    private String pname;//产品名称
    private String flag;//表的标记

    //反序列化时要反射调用空参构造方法，所以必须有
    public TableBean() {
        super();
    }

    //全参构造方法
    public TableBean(String order_id, String p_id, int amount, String pname, String flag) {
        this.order_id = order_id;
        this.p_id = p_id;
        this.amount = amount;
        this.pname = pname;
        this.flag = flag;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    //序列化写入
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(order_id);
        out.writeUTF(p_id);
        out.writeInt(amount);
        out.writeUTF(pname);
        out.writeUTF(flag);
    }

    //反序列化，顺序一定要和序列化顺序一致
    @Override
    public void readFields(DataInput in) throws IOException {

        order_id = in.readUTF();
        p_id = in.readUTF();
        amount = in.readInt();
        pname = in.readUTF();
        flag = in.readUTF();
    }

    //tostring方法，便于写出
    @Override
    public String toString() {
        return order_id + "\t" + pname + "\t" + amount + "\t";
    }
}
