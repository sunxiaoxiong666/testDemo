package mapReduce.NLineInputFormat;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class NLineMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    Text k = new Text();
    LongWritable v = new LongWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取一行数据
        String line = value.toString();
        //2.切割
        String[] fields = line.split(" ");
        //3.循环写出
        for (int i = 0; i < fields.length; i++) {
            k.set(fields[i]);
            context.write(k, v);
        }
    }
}