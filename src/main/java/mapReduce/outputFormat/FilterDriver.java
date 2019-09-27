package mapReduce.outputFormat;

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
* 需求
过滤输入的 log 日志中是否包含 atguigu
（1）包含 atguigu 的网站输出到 e:/atguigu.log
（2）不包含 atguigu 的网站输出到 e:/other.log
* */
public class FilterDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{"file:/D:/input/outputformat", "file:/D:/out"};

        //1.读取配置信息，得到job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置加载的类驱动
        job.setJarByClass(FilterDriver.class);
        //3.设置mapper和reducer
        job.setMapperClass(FilterMapper.class);
        job.setReducerClass(FilterReducer.class);
        //4.设置mapper输出的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        //5.设置reducer最终输出的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        //6.设置输入和输出的路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //8.设置自定义的outputFormat
        //虽然我们自定义了 outputformat，但是因为我们的 outputformat 继承自fileoutputformat
        // 而 fileoutputformat 要输出一个_SUCCESS 文件，所以，在这还得指定一个输出目录
        job.setOutputFormatClass(FilterOutPutFormat.class);
        //7.提交任务
        deleteDir("D:/out");
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
