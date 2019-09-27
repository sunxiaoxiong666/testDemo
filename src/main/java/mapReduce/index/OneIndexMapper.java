package mapReduce.index;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class OneIndexMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    String name = null;
    Text k = new Text();
    IntWritable v = new IntWritable();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //.获取文件名称
        FileSplit split = (FileSplit) context.getInputSplit();
        name = split.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //1.获取一行数据
        String line = value.toString();
        //2.分割数据
        String[] fislds = line.split("\t");
        //3.拼接数据
        for (String field : fislds) {
            k.set(field + "--" + name);
            v.set(1);
            //4.写出
            context.write(k, v);
        }
    }
}
