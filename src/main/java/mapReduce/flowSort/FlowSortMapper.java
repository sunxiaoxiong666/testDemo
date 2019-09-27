package mapReduce.flowSort;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
 * 因为要对流量总和进行排序，所以把FlowBean作为输出的key
 * */
public class FlowSortMapper extends Mapper<LongWritable, Text, FlowBean, Text> {

    FlowBean k = new FlowBean();
    Text v = new Text();

    //输入的key为行号，value为 1363157993055	13560436666	C4-17-FE-BA-DE-D9:CMCC	120.196.100.99	18	15	1116	954	200
    //输出的key为 1116	954  2070  value为13560436666
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取一行数据
        String str = value.toString();
        //2.分割数据
        String[] words = str.split("\t");
        //3.封装对象
        long upFlow = Long.parseLong(words[1]);
        long downFlow = Long.parseLong(words[2]);
        k.set(upFlow, downFlow);
        v.set(words[0]);
        //4.写出数据
        context.write(k, v);
    }
}
