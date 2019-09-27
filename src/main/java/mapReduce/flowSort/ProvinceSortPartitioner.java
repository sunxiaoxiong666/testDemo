package mapReduce.flowSort;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ProvinceSortPartitioner extends Partitioner<FlowBean, Text> {
    @Override
    public int getPartition(FlowBean flowBean, Text text, int numPartitions) {
        //1.获取电话号码的前三位
        String phoneNum = text.toString().substring(0, 3);

        int partion = 4;
        if ("136".equals(phoneNum)) {
            partion = 0;
        } else if ("137".equals(phoneNum)) {
            partion = 1;
        } else if ("138".equals(phoneNum)) {
            partion = 2;
        } else if ("139".equals(phoneNum)) {
            partion = 3;
        } else {
            partion = 4;
        }
        return partion;
    }
}
