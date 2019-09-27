package mapReduce.friendShare;

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
找共同好友案例
经过两次mapreduce
* 第一次driver，最终输出的结果为<好友，<人，人，人>>
* 第二次最终输出<<人，人>，<好友，好友，好友....>>
* */
public class OneFriendShareDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{"d:/input/friendshare", "d:/out"};

        //1.获得job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置驱动类
        job.setJarByClass(OneFriendShareDriver.class);
        //3.设置mapper和reducer
        job.setMapperClass(OneFriendShareMapper.class);
        job.setReducerClass(OneFriendShareReducer.class);
        //4.设置mapper的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //5.设置reducer最终输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //6.设置输入和输出路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //7.提交job
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
