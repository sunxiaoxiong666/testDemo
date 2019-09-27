package mapReduce.kvText;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class KvtextMapper extends Mapper<Text, Text, Text, LongWritable> {

    Text k = new Text();
    LongWritable v = new LongWritable();

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        //banzhang ni hao
        //key为banzhang,value为ni hao
        //1.设置key
        k.set(key);
        //设置key的个数
        v.set(1);
        //2.写出
        context.write(k, v);
    }
}
