package mapReduce.order;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class OrderSortMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

    OrderBean orderBean = new OrderBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1、获取一行数据
        String str = value.toString();
        //2.分割数据
        String[] values = str.split("\t");
        //3.封装对象
        orderBean.setOrder_id(Integer.parseInt(values[0]));
        orderBean.setPrice(Double.parseDouble(values[2]));
        //4.写出
        context.write(orderBean, NullWritable.get());
    }
}
