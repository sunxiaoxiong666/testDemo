package mapReduce.outputFormat;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

//自定义具体写数据的RecordWriter
public class FilterRecordWriter extends RecordWriter<Text, NullWritable> {
    FSDataOutputStream atguiguOut = null;
    FSDataOutputStream otherOut = null;

    //含参数的构造方法
    public FilterRecordWriter(TaskAttemptContext job) {

        try {
            //1.获取文件系统
            FileSystem fs = FileSystem.get(job.getConfiguration());
            //2.创建文件输出路径
            //注意：在本地运行，则将4个xml文件移除即可，否则报错。
            Path atguiguLog = new Path("file:/D:/out/atguigu.log");
            Path otherLog = new Path("file:/D:/out/other.log");
            //3.创建输出流
            atguiguOut = fs.create(atguiguLog);
            otherOut = fs.create(otherLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        //判断是否包含“atguigu”，输出到不同的文件中
        if (key.toString().contains("atguigu")) {
            atguiguOut.write(key.toString().getBytes());
        } else {
            otherOut.write(key.toString().getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {

        //关闭资源
        if (atguiguOut != null) {
            atguiguOut.close();
        }

        if (otherOut != null) {
            otherOut.close();
        }
    }
}
