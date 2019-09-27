package mapReduce.friendShare;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
 * 第二次reducer
 * 输入为：<人-人，好友>
 * 输出为：<人-人，<好友，好友...>>
 *
 * */
public class TwoFriendShareReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //将两个人之间的共同好友拼接起来
        StringBuilder sb = new StringBuilder();
        for (Text friend : values) {
            sb.append(friend).append(",");
        }
        context.write(key, new Text(sb.toString()));
    }
}
