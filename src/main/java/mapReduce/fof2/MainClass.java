package mapReduce.fof2;

/*
 * @author: sunxiaoxiong
 */

import mapReduce.fof.FofMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MainClass {

    public static void step1(){
        try {
            //加载配置文件，true表示加载类路径下的配置信息
            Configuration conf = new Configuration(true);
            //创建job对象
            Job job = Job.getInstance(conf);
            //设置主入口类
            job.setJarByClass(MainClass.class);
            //设置输入路径
            FileInputFormat.addInputPath(job, new Path("/xiong_test2/fof.txt"));
            //设置输出路径
            FileOutputFormat.setOutputPath(job, new Path("/xiong_test2/fof"));
            //添加mapper和reducer类
            job.setMapperClass(FofMapper.class);
            job.setReducerClass(FofReducer.class);
            //设置输出key，value的数据类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            //提交任务
            job.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void step2(){
        try {
            Configuration conf = new Configuration(true);
            Job job = Job.getInstance(conf);
            job.setJarByClass(MainClass.class);
            FileInputFormat.addInputPath(job, new Path("/xiong_test2/fof"));
            FileOutputFormat.setOutputPath(job, new Path("/xiong_test2/fof2"));
            job.setMapperClass(FofMapper2.class);
            job.setReducerClass(FofReduser2.class);
            job.setOutputKeyClass(Fof.class);
            //添加分组比较器
            job.setGroupingComparatorClass(Fof2GroupingComparator.class);
            //添加排序比较器
            job.setSortComparatorClass(Fof2SortComparator.class);
            job.setOutputValueClass(IntWritable.class);
            job.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try {
            step1();
            step2();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
