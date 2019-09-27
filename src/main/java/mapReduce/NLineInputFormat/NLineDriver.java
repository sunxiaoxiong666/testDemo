package mapReduce.NLineInputFormat;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
*
* 7.12 NLineInputFormat 使用案例,
1） 需求： 根据每个输入文件的行数来规定输出多少个切片。 例如每三行放入一个切片中。
2）输入数据：
banzhang ni hao
xihuan hadoop banzhang dc
banzhang ni hao
xihuan hadoop banzhang dc
banzhang ni hao
xihuan hadoop banzhang dc
banzhang ni hao
xihuan hadoop banzhang dc
banzhang ni hao
xihuan hadoop banzhang dcbanzhang ni hao
xihuan hadoop banzhang dc
3） 输出结果：
Number of splits:4
* */
public class NLineDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{"d:/input/nline", "d:/out"};

        //1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //设置每个切片中有3条记录
        NLineInputFormat.setNumLinesPerSplit(job, 3);
        //使用NLineInputFormat处理记录数
        job.setInputFormatClass(NLineInputFormat.class);

        //2.设置驱动类
        job.setJarByClass(NLineDriver.class);
        //3.设置mapper和reducer
        job.setMapperClass(NLineMapper.class);
        job.setReducerClass(NLineReducer.class);
        //4.设置mapper的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        //5.设置reducer的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        //6.设置输入和输出路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //7.提交job
        job.waitForCompletion(true);
    }
}
