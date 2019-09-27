package mapReduce.weather;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

//用于对数据分区，一个分区对应一个reducer
public class WeatherPartitioner extends Partitioner<Weather, Text> {

    //reducer任务和reduce方法
    @Override
    public int getPartition(Weather key, Text value, int numPartitions) {
        System.out.println("分区" + "key" + key + "value" + value + "numpart" + numPartitions);
        //按月份分区
        return key.getMonth() % numPartitions;
    }
}
