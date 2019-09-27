package mapReduce.flow;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    FlowBean flowBean = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        long upFlow = 0;
        long downFlow = 0;
        //1.遍历所有的bean，将其中的上行流量和下行流量累加
        for (FlowBean bean : values) {
            upFlow += bean.getUpFlow();
            downFlow += bean.getDownFlow();
        }
        //2.封装对象
        flowBean.set(upFlow, downFlow);
        //3.写出
        context.write(key, flowBean);
    }
}
