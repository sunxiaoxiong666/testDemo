package mapReduce.flowSort;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
 * 输入的key为 1116	954  2070  value为13560436666
 * 输出的key为13560436666 value为1116	954  2070
 * */
public class FlowSortReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //因为输入的数据为上个mapreduce输出的结果，就是单条的，所以直接输出结果
        context.write(values.iterator().next(), key);
    }
}
