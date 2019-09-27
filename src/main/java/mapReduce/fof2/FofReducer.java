package mapReduce.fof2;

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
                //他们不直接认识
                num += numWritable.get();
            } else {
                //他们直接认识
                flag = false;
                break;
            }
        }
        if (flag) {
            //如果不直接认识，就写到输出，否则不写
            context.write(key, new IntWritable(num));
        }
    }
}
