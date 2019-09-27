package mapReduce.weather;

/*
 * @author: sunxiaoxiong
 */


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
* 找出每个月气温最高的两天
* 1949-10-01 14:21:02	34c
  1949-10-01 19:21:02	38c
  1949-10-02 14:01:02	36c
  1950-01-01 11:21:02	32c
  1950-10-01 12:21:02	37c
  1951-12-01 12:21:02	23c
  1950-10-02 12:21:02	41c
  1950-10-03 12:21:02	27c
  1951-07-01 12:21:02	45c
  1951-07-02 12:21:02	46c
  1951-07-03 12:21:03	47c
* */
public class MainClass {
    public static void main(String[] args) throws Exception {
        //准备配置信息，读取classpath下的配置文件的信息，替换默认的值
        Configuration conf = new Configuration(true);
        //创建job对象
        Job job = Job.getInstance(conf);
        //设置主入口程序
        job.setJarByClass(MainClass.class);
        //设置输入路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        //设置输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //添加mapper和reducer类
        job.setMapperClass(WeatherMapper.class);
        job.setReducerClass(WeatherReducer.class);
        //设置输出的k,v的数据类型
        job.setMapOutputKeyClass(Weather.class);
        job.setMapOutputValueClass(Text.class);
        //设置分区器
        job.setPartitionerClass(WeatherPartitioner.class);
        //设置分组比较器
        job.setGroupingComparatorClass(WeatherGroupingComparator.class);
        //排序比较器 map输出要排序，需要使用比较器
        job.setSortComparatorClass(WeatherSortComparator.class);
        //设置reducer数量
        job.setNumReduceTasks(2);
        //提交作业
        job.waitForCompletion(true);
    }
}
