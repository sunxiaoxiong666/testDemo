package mapReduce.NLineInputFormat;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class NLineReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

    LongWritable v = new LongWritable();

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0;
        //1.汇总
        for (LongWritable value : values) {
            count += value.get();
        }
        v.set(count);
        context.write(key, v);
    }
}
