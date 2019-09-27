package mapReduce.flow;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


/*
* 1363157993055     13560436666     C4-17-FE-BA-DE-D9:CMCC  120.196.100.99  18  15  1116    954         200
                    手机号码                                                         上行流量 下行流量
* */
public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    FlowBean bean = new FlowBean();

    //key为行号，value为该行数据
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行数据
        String str = value.toString();
        //通过\t分割数据
        String[] values = str.split("\t");
        //获取上行流量
        String upFlow = values[values.length - 3];
        //获取下行流量
        String downFlow = values[values.length - 2];
        //获取手机号
        String phoneNum = values[1];
        //封装对象
        bean.set(Long.parseLong(upFlow), Long.parseLong(downFlow));
        //写出
        context.write(new Text(phoneNum), bean);
    }
}
