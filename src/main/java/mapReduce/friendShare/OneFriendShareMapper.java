package mapReduce.friendShare;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
 * 第一次mapper
 * 输出：<好友，人>
 * */
public class OneFriendShareMapper extends Mapper<LongWritable, Text, Text, Text> {

    Text k = new Text();
    Text v = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取一行数据,冒号前面是人，后面是他的好友
        //A:B,C,D,E,F
        String line = value.toString();
        //2.切割数据
        String[] values = line.split(":");
        //3.获取人和好友
        String person = values[0];//人
        String[] friends = values[1].split(",");//好友
        //4.封装k，v，写出
        for (String friend : friends) {
            k.set(friend);
            v.set(person);
            context.write(k, v);
        }
    }
}
