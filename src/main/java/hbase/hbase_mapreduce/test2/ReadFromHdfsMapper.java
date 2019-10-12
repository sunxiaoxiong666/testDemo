package hbase.hbase_mapreduce.test2;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/12 15:36
 */

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
/*
 * 目标：读取hdfs中的数写入到hbase表中
 * */

//读取hdfs中文件数据
public class ReadFromHdfsMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //从hdfs中读取一行数据
        String str = value.toString();
        //分割数据
        String[] values = str.split("\t");
        //根据数据中值的含义取值
        String rowKey = values[0];
        String name = values[1];
        String color = values[2];
        //初始化rowKey
        ImmutableBytesWritable row = new ImmutableBytesWritable(Bytes.toBytes(rowKey));
        //初始化put对象
        Put put = new Put(Bytes.toBytes(rowKey));
        //参数分别为：列族，列，值
        put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(name));
        put.add(Bytes.toBytes("info"), Bytes.toBytes("color"), Bytes.toBytes(color));
        context.write(row, put);
    }
}