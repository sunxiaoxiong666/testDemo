package mapReduce.friendShare;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;

/*
 * 第二次mapper
 * 输入为第一次的输出：<好友，<人，人...>>
 * 输出为：<<人-人>，好友>
 * */
public class TwoFriendShareMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取一行数据
        String line = value.toString();
        //2.切割数据
        String[] values = line.split("\t");
        String friend = values[0];//好友
        String[] persons = values[1].split(",");//人
        //3.封装数据
        Arrays.sort(persons);//将人名排下顺序
        for (int i = 0; i < persons.length; i++) {
            for (int j = i + 1; j < persons.length; j++) {
                // 发出 <人-人，好友> ，这样，相同的“人-人”对的所有好友就会到同 1 个 reduce 中去
                context.write(new Text(persons[i] + "-" + persons[j]), new Text(friend));
            }
        }

    }
}
