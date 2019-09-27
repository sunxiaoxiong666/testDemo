package mapReduce.flowSort;

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
 * 将统计结果按照总流量倒序排序（全排序）
 * 把程序分两步走，第一步正常统计总流量， 第二步再把结果进行排序
 * */
public class FlowSortDriver {
    public static void main(String[] args) throws Exception {
        //1.获取配置信息，得到job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置加载的驱动类
        job.setJarByClass(FlowSortDriver.class);
        //3.设置mapper和reducer
        job.setMapperClass(FlowSortMapper.class);
        job.setReducerClass(FlowSortReducer.class);
        //4.设置mapper输出的数据类型
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);
        //5.设置最终输出的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //8.要求每个省份手机号输出的文件中按照总流量内部排序。(部分排序)
        job.setPartitionerClass(ProvinceSortPartitioner.class);
        job.setNumReduceTasks(5);

        //6.设置输入路径和输出路劲
        FileInputFormat.addInputPath(job, new Path("file:\\D:\\out"));
        FileOutputFormat.setOutputPath(job, new Path("file:\\D:\\out2"));
        //7.提交job
        job.waitForCompletion(true);
    }
}
