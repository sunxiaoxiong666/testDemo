package mapReduce.order;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class OrderSortReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable> {
    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        //因为经过了分区，排序，输入的就是想要的结果，直接输出就行
        context.write(key, NullWritable.get());
    }
}
