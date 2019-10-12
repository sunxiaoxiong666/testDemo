package hbase.hbase_mapreduce.test1;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/12 14:24
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FruitDriver extends Configured implements Tool {
    //组装job
    @Override
    public int run(String[] args) throws Exception {
        //得到Configuration
        Configuration conf = this.getConf();
        //创建job
        Job job = Job.getInstance(conf, this.getClass().getSimpleName());
        job.setJarByClass(FruitDriver.class);
        //配置job
        Scan scan = new Scan();
        scan.setCacheBlocks(false);
        scan.setCaching(500);
        //设置mapper
        TableMapReduceUtil.initTableMapperJob(
                "fruit",//数据源的表名
                scan,//扫描控制器
                ReadFruitMapper.class,//设置mapper类
                ImmutableBytesWritable.class,//设置mapper输出key类型
                Put.class,//设置mapper输出的value类型
                job//设置给那个job
        );
        //设置reducer
        TableMapReduceUtil.initTableReducerJob("fruit_mr", WriteFruitReduce.class, job);
        //设置reducer的数量，最少是1个
        job.setNumReduceTasks(1);
        boolean isSuccess = job.waitForCompletion(true);
        return isSuccess ? 1 : 0;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        int result = ToolRunner.run(conf, new FruitDriver(), args);
        System.out.println(result);
        System.exit(result);
    }
}
