package mapReduce.order;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;

public class OrderDriver {

    public static void main(String[] args) throws Exception {
        //1.获得配置信息，得到job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置加载的驱动类
        job.setJarByClass(OrderBean.class);
        //3.设置mapper和reducer
        job.setMapperClass(OrderSortMapper.class);
        job.setReducerClass(OrderSortReducer.class);
        //4.设置mapper的输出数据类型
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);
        //5.设置最终输出的数据类型
        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);
        //6.设置输入路径和输出路径
        FileInputFormat.addInputPath(job, new Path("file:\\D:\\input\\order"));
        FileOutputFormat.setOutputPath(job, new Path("file:/D:/out"));
        //7.设置reducer端的分组
        job.setGroupingComparatorClass(OederSortGroupingComparator.class);
        //8.设置分区
        job.setPartitionerClass(OrderSortPartitioner.class);
        //9.设置reducetask个数
        job.setNumReduceTasks(3);
        //10.提交任务
        deleteDir("D:\\out");
        job.waitForCompletion(true);
    }

    /**
     * 迭代删除文件夹
     *
     * @param dirPath 文件夹路径
     */
    public static void deleteDir(String dirPath) {
        File file = new File(dirPath);
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                file.delete();
            } else {
                for (int i = 0; i < files.length; i++) {
                    deleteDir(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }
}
