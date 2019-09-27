package mapReduce.order;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class OederSortGroupingComparator extends WritableComparator {

    //1。空参构造方法，没有会报错
    public OederSortGroupingComparator() {
        super(OrderBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean orderBeana = (OrderBean) a;
        OrderBean orderBeanb = (OrderBean) b;
        //根据订单编号比较，判断是否为一组
        int result = 0;
        if (orderBeana.getOrder_id() > orderBeanb.getOrder_id()) {
            result = 1;
        } else if (orderBeana.getOrder_id() < orderBeanb.getOrder_id()) {
            result = -1;
        } else {
            result = 0;
        }
        return result;
    }
}
