package hbase.hbase_mapreduce.test2;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/12 15:56
 */

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

//将数据写入到表中
public class ReadFromHdfsReducer extends TableReducer<ImmutableBytesWritable, Put, NullWritable> {
    @Override
    protected void reduce(ImmutableBytesWritable key, Iterable<Put> values, Context context) throws IOException, InterruptedException {
        //读出来每一行数据写入到表中
        for (Put put : values) {
            context.write(NullWritable.get(), put);
        }
    }
}
