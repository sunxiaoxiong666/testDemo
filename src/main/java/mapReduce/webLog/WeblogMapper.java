package mapReduce.webLog;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WeblogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    Text k = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.读取一行数据
        String line = value.toString();

        //3.清洗数据
        Boolean result = cleanDate(line, context);

        //4.日志不符合条件就退出
        if (!result) {
            return;
        }
        //5.设置key
        k.set(line);
        //6.写出
        context.write(k, NullWritable.get());
    }

    //清洗数据
    private boolean cleanDate(String line, Context context) {
        //2.分割数据
        String[] fields = line.split(" ");
        //判断字段个数是否大于3
        if (fields.length > 3) {
            //系统计数器，每次执行该行代码，就会加1
            context.getCounter("weblog", "true").increment(1);
            return true;
        } else {
            context.getCounter("weblog", "false").increment(1);
            return false;
        }
    }
}
