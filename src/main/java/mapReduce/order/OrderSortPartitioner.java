package mapReduce.order;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class OrderSortPartitioner extends Partitioner<OrderBean, NullWritable> {

    //确定分区
    @Override
    public int getPartition(OrderBean key, NullWritable value, int numReducerTask) {
        // 按照key的orderid的hashCode值分区
        return (key.getOrder_id() & Integer.MAX_VALUE) % numReducerTask;
    }
}
