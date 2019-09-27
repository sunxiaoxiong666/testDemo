package mapReduce.fof2;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class FofReduser2 extends Reducer<Fof, IntWritable, Text, IntWritable> {

    private Text outKey = new Text();

    @Override
    protected void reduce(Fof key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<IntWritable> itera = values.iterator();
        int num = 0;
        IntWritable value = null;
        while (itera.hasNext()) {
            if (num > 0) {
                break;
            }
            //这里以上不要写代码，还没有取出当前键值对
            value = itera.next();
            //此处已经取出当前键值对
            outKey.set(key.getAname() + "-" + key.getBname());
            System.out.println("===================");
            context.write(outKey, value);
            num++;
        }
    }
}
