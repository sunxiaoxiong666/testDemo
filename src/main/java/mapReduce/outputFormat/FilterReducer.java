package mapReduce.outputFormat;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FilterReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        //1.将输出的key按换行分割
        String k = key.toString();
        k = k + "\r\n";

        //2.输出
        context.write(new Text(k), NullWritable.get());
    }
}
