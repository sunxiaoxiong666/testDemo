package mapReduce.flow;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/*
 * 需求： 将统计结果按照手机归属地不同省份输出到不同文件中（分区）
 * 自定义一个分区类
 * */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {
    //text，flowbean为mapper输出的key，value
    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
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
