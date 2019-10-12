package hbase.hbase_mapreduce.test1;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/12 10:13
 */

/*
 * 目标：将fruit表中的一部分数据，通过MR迁入到fruit_mr表中
 * */

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

//创建mapper类，用于读取fruit表中的数据
public class ReadFruitMapper extends TableMapper<ImmutableBytesWritable, Put> {
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        //将fruit表的name和color提取出来，相当于将每一行数据读取出来放入到put对象中
        Put put = new Put(key.get());
        //遍历添加column行
        Cell[] cells = value.rawCells();
        for (Cell cell : cells) {
            //添加列族 info
            if ("info".equals(Bytes.toString(CellUtil.cloneFamily(cell)))) {
                //添加列 name
                if ("name".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                    //将该列添加到put中
                    put.add(cell);
                    //添加列 color
                } else if ("color".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                    put.add(cell);
                }
            }
        }
        //将从fruit读取的数据写到context中，作为map的输出
        context.write(key, put);
    }
}