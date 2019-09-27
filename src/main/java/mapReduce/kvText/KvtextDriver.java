package mapReduce.kvText;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
KeyValueTextInputFormat的使用案例，将一行数据按照某个字符串分割出key，value
* 1)需求：统计输入文件中每一行的第一个单词相同的行数。
2）输入文件：
banzhang ni hao
xihuan hadoop banzhang dc
banzhang ni hao
xihuan hadoop banzhang dc
3)输出
banzhang 2
xihuan 2
* */
public class KvtextDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{"d:/input/kvtext", "d:/out"};

        //1.获得job对象
        Configuration conf = new Configuration();

        //设置分隔符，将一行数据分割成key和value
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, " ");
        Job job = Job.getInstance(conf);

        //设置输入格式
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        //2.设置mapper和reducer
        job.setMapperClass(KvtextMapper.class);
        job.setReducerClass(KvtextReducer.class);
        //3.设置驱动类
        job.setJarByClass(KvtextDriver.class);
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
