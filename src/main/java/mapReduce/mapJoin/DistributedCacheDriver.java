package mapReduce.mapJoin;

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
import java.net.URI;

public class DistributedCacheDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{"d:/input/mapJoin", "d:/out"};

        //1.获得配置文件，得到job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置驱动类
        job.setJarByClass(DistributedCacheDriver.class);
        //3.设置mapper
        job.setMapperClass(DistributedCacheMapper.class);
        //4.设置最终输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        //5.设置输入和输出路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //6.加载缓存数据
        job.addCacheFile(new URI("file:///d:/input/reduceJoin/pd.txt"));
        //7.在map端join时，不需要reduce阶段，设置reducetask数量为0
        job.setNumReduceTasks(0);
        //8.提交job
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
