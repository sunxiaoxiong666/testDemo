package mapReduce.inputFormat;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;


/*
* 小文件处理案例，将多个小文件合并为一个文件输出
* */
public class WholeDriver {
    public static void main(String[] args) throws Exception {
        //1.获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置驱动类
        job.setJarByClass(WholeDriver.class);
        //3.设置mapper和reducer
        job.setMapperClass(WholeMapper.class);
        job.setReducerClass(WholeReducer.class);
        //4.设置输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);

        //8.设置自定义输入的inputformat
        job.setInputFormatClass(WholeFileInputFormat.class);
        //设置输出的outputformat
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        //5设置输入，输出路径
        FileInputFormat.addInputPath(job, new Path("/xiong_test2/whole"));
        FileOutputFormat.setOutputPath(job, new Path("/xiong_test2/whole/out"));

        //6.提交
        job.waitForCompletion(true);
    }
}
