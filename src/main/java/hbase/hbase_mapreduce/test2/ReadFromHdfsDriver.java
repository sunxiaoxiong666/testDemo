package hbase.hbase_mapreduce.test2;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/12 16:00
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class ReadFromHdfsDriver extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        //得到configuration
        Configuration conf = this.getConf();
        //创建job
        Job job = Job.getInstance(conf, this.getClass().getSimpleName());
        job.setJarByClass(ReadFromHdfsDriver.class);
        Path path = new Path("/input_fruit/fruit.tsv");
        FileInputFormat.addInputPath(job, path);

        //设置mapper
        job.setMapperClass(ReadFromHdfsMapper.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(Put.class);
        //设置reducer
        TableMapReduceUtil.initTableReducerJob("fruit_mr", ReadFromHdfsReducer.class, job);
        //设置reducer数量，最少为1个
        job.setNumReduceTasks(1);

        boolean result = job.waitForCompletion(true);
        return result ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        int result = ToolRunner.run(conf, new ReadFromHdfsDriver(), args);
        System.out.println(result);
        System.exit(result);

    }
}
