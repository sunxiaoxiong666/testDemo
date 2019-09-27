package mapReduce.wordCount;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    Text k = new Text();
    IntWritable v = new IntWritable(1);

    /*
     * key为每行的下标
     * value为每行单词
     * */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将每行单词转换为字符串
        String str = value.toString();
        //将字符串通过空格分割为字符数组
        String[] words = str.split(" ");
        for (String word : words) {
            //此写法效率会低点,每次都要创建对象
            // context.write(new Text(word), new IntWritable(1));
            k.set(word);
            context.write(k, v);
        }
    }
}
