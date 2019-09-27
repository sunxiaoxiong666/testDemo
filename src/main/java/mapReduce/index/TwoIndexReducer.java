package mapReduce.index;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
* 第二次reducer
*
* 输入为：
atguigu a.txt 3
atguigu b.txt 2
atguigu c.txt 2
*
* */
public class TwoIndexReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //1.拼接value
        StringBuilder sb = new StringBuilder();
        for (Text value : values) {
            sb.append(value.toString().replace("\t", "-->") + "\t");
        }
        //2.写出
        context.write(key, new Text(sb.toString()));
    }
}
