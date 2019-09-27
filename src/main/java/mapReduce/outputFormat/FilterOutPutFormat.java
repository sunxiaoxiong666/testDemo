package mapReduce.outputFormat;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

//自定义outputFormat继承FileOutputFormat
public class FilterOutPutFormat extends FileOutputFormat<Text, NullWritable> {

    //重写方法，创建一个RecordWriter
    @Override
    public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {

        //返回自定义的recordWriter
        return new FilterRecordWriter(job);
    }
}
