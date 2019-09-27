package mapReduce.wordCount;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;

/*
 * 统计文件中每个单词的个数
 * */
public class WordCountDriver {
    public static void main(String[] args) {
        try {
            //1.获取配置信息，得到job对象
            Configuration conf = new Configuration();

            //开启map端输出压缩
            conf.setBoolean("mapreduce.map.output.compress", true);
            //设置map端输出压缩方式
            conf.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class, CompressionCodec.class);

            Job job = Job.getInstance(conf);
            //2.设置运行java类
            job.setJarByClass(WordCountDriver.class);
            //3.设置mapper和reducer
            job.setMapperClass(WordCountMapper.class);
            job.setReducerClass(WordCountReducer.class);
            //4.设置mapper的输出类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            //5.设置reducer的输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            //6.设置输入和输出路径
            //注意：读取本地文件时要在路径前面加file:\\
            FileInputFormat.addInputPath(job, new Path("file:\\D:\\input\\wordcount"));
            FileOutputFormat.setOutputPath(job, new Path("file:\\D:\\out"));

            // 设置 reduce 端输出压缩开启
            FileOutputFormat.setCompressOutput(job, true);
            // 设置压缩的方式
            FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
            // FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
            // FileOutputFormat.setOutputCompressorClass(job, DefaultCodec.class);

            //8.当输入目录中有5个小文件时，就会生成5个切片--number of splits:5；会影响效率。
            //将小文件合并成一个大文件，经过下方的设置后，就只生成一个切片---number of splits:1
            job.setInputFormatClass(CombineTextInputFormat.class);//如果不设置InputFormat，默认使用FileInputFormat
            CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);//设置最大切片的大小为4m
            CombineTextInputFormat.setMinInputSplitSize(job, 2097152);//设置最小切片的大小为2m

            //9.指定combiner
            /*指定combiner后，输出显示，即将maptask的输出结果进行了合并
            Combine input records=50
            Combine output records=7

            没有指定combiner时，输出显示
            Combine input records=0
		    Combine output records=0
            */
            // job.setCombinerClass(WordcountCombiner.class);
            //观察到WordcountCombiner和WordcountReducer中的代码逻辑是一样的，所以也可以将WordcountCombiner作为combiner
            job.setCombinerClass(WordCountReducer.class);

            //7.提交任务
            //删除输出目录
            deleteDir("D:\\out");
            job.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
