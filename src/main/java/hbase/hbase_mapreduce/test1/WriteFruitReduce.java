package hbase.hbase_mapreduce.test1;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/12 13:50
 */

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

//构建reducer类，用于将读取的数据写入到fruit_mr表中
public class WriteFruitReduce extends TableReducer<ImmutableBytesWritable, Put, NullWritable> {

    @Override
    protected void reduce(ImmutableBytesWritable key, Iterable<Put> values, Context context) throws IOException, InterruptedException {
        //将读出的每一行数据，写入到fruit_mr表中
        for (Put put : values) {
            context.write(NullWritable.get(), put);
        }
    }
}
