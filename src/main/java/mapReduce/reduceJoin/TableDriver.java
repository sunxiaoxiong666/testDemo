package mapReduce.reduceJoin;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;

/*
* 在reduce端进行两个表(文件)的合并，容易发生数据倾斜
 order表：
 1001	01	1
1001	01	2
1002	02	3
1002	02	4
1003	03	5
1003	03	6

产品表：
01	华为
02	大疆
03	geli

最终结果：
1001	华为  	2
1001	华为	    1
1002	大疆	    4
1002	大疆	    3
1003	geli	6
1003	geli	5

*
* */
public class TableDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{"d:/input/reduceJoin", "d:/out"};

        //1.读取配置信息，获得job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置驱动类
        job.setJarByClass(TableDriver.class);
        //3.设置mapper和reducer
        job.setMapperClass(TableMapper.class);
        job.setReducerClass(TableReducer.class);
        //4.设置mapper的输出数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);
        //5.设置最终输出的数据类型
        job.setOutputKeyClass(TableBean.class);
        job.setOutputValueClass(NullWritable.class);
        //6.设置输入和输出的路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //7.提交任务
        deleteDir(args[1]);
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
