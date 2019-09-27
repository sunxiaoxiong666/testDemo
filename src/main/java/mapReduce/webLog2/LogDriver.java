package mapReduce.webLog2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
 * @author: sunxiaoxiong
 */
/*
 * 对日志中每行记录的字段进行清洗
 * */
public class LogDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{"d:/input/logweb2", "d:/out"};

        //1.得到配置信息，获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置驱动类
        job.setJarByClass(LogDriver.class);

        //3.设置mapper，不需要reduce，设置reducetask的数量为0
        job.setMapperClass(LogMapper.class);
        job.setNumReduceTasks(0);

        //4.设置输出的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //5.设置输入和输出的路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //6.提交job
        job.waitForCompletion(true);
    }
}
