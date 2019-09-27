package mapReduce.wordCount;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    IntWritable value = new IntWritable();

    /*
    每一组key值相同的键值对，进行一次reduce操作
    aa  1
    aa  1
    得到  aa  2
    bb 1
    bb 1
    得到  bb 2
        * */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) {
        try {
            int sum = 0;
            for (IntWritable v : values) {
                sum += v.get();
            }
            value.set(sum);
            context.write(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
