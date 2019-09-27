package mapReduce.inputFormat;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class WholeMapper extends Mapper<NullWritable, BytesWritable, Text, BytesWritable> {

    Text k = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //1.获取文件切片信息
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        //2.获取切片名称
        String name = inputSplit.getPath().toString();
        //3.设置key的输出
        k.set(name);
    }

    @Override
    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
        //控制输出数据
        context.write(k, value);
    }
}
