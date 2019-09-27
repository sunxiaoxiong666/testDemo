package mapReduce.webLog;

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
 * 对日志文件进行清洗，去除每行记录中字段个数小于3的行
 * */
public class WeblogDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{"d:/input/weblog", "d:/out"};

        //1.读取配置信息，得到job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置驱动类
        job.setJarByClass(WeblogDriver.class);

        //3.设置mapper，直接输出结果，不需要reducer
        job.setMapperClass(WeblogMapper.class);
        //设置reducetask的个数为0
        job.setNumReduceTasks(0);

        //4.设置输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //5.设置输入和输出路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //6.提交job
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
