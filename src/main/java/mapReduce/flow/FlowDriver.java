package mapReduce.flow;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;

/*
 * 统计手机号耗费的总上行流量、下行流量、 总流量
 * */
public class FlowDriver {
    public static void main(String[] args) throws Exception {
        //1.读取配置信息，得到job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置运行的驱动类
        job.setJarByClass(FlowDriver.class);
        //3.设置mapper和reducer类
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        //4.设置mapper的输出数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        //5.设置reducer的输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        //6.设置输入路径和输出路径
        FileInputFormat.addInputPath(job, new Path("file:\\D:\\input\\flow"));
        FileOutputFormat.setOutputPath(job, new Path("file:\\D:\\out"));

        //9.指定自定义 数据分区的设置
      /*  job.setPartitionerClass(ProvincePartitioner.class);
        //10.同时设置响应数量的reducetask的数量
        *//*注意：1.当reducetask的数量等于分区数量，则输出结果在一个文件中
            2.当1<reducetask的数量<分区数量，就会报错，因为分区不知道去哪个reducetask上运行
            3.当reducetask数量>分数数量，正常运行，会多出空文件夹
        *//*
        job.setNumReduceTasks(6);*/

        //7.判断hdfs上的输出路径是否存在
      /*  FileSystem fs = FileSystem.get(new URI(args[1]), conf);
        if (fs.exists(new Path(args[1]))) {
            fs.delete(new Path(args[1]), true);
        }*/
        //删除本地的输出路径
        deleteDir("D:\\out");
        //8.提交任务
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
