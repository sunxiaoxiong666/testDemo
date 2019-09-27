package mapReduce.friendShare;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
 * 第一次reducer
 * 输入为：<好友，人>
 * 输出为：<好友，<人，人，人>>
 * */
public class OneFriendShareReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //1.拼接
        StringBuilder sb = new StringBuilder();
        for (Text value : values) {
            sb.append(value).append(",");
        }
        //2.写出
        context.write(key, new Text(sb.toString()));
    }
}
