package mapReduce.fof;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
 * 好友推荐
 * tom hello hadoop cat
   world hadoop hello hive
   cat tom hive
   mr hive hello
   hive cat hadoop world hello mr
   hadoop tom hive world
   hello tom world hive mr
 * */
public class MainClass {
    public static void main(String[] args) throws Exception {

        if (args == null || args.length != 2) {
            System.out.println("参数不正确");
            return;
        }
        try {
            //加载配置文件，true表示加载类路径下的配置信息，就会找到resources文件夹中的xml配置文件
            Configuration conf = new Configuration(true);
            //创建job对象
            Job job = Job.getInstance(conf);
            //设置主入口类
            job.setJarByClass(MainClass.class);
            //设置输入路径
            FileInputFormat.addInputPath(job, new Path(args[0]));
            //设置输出路径
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            //添加mapper和reducer类
            job.setMapperClass(FofMapper.class);
            job.setReducerClass(FofReducer.class);
            //设置输出的key和value的类型,不设置会报错
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            //提交作业
            job.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
