package mapReduce.fof;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FofReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int num = 0;
        boolean flag = true;
        for (IntWritable numWritable : values) {
            if (numWritable.get() != 0) {
                //他们不认识的时候
                num += numWritable.get();
            } else {
                //他们认识
                flag = false;
                break;
            }
        }
        if (flag) {
            //如果他们不认识，则直接写到输出中，否则不写
            context.write(key, new IntWritable(num));
        }
    }
}
