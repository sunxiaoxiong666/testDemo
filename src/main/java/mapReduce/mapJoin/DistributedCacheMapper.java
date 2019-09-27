package mapReduce.mapJoin;

/*
 * @author: sunxiaoxiong
 */

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DistributedCacheMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    Map<String, String> pdMap = new HashMap<>();
    Text k = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //1.读取缓存的文件
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("pd.txt"), "UTF-8"));
        String line;
        while (StringUtils.isNotEmpty(line = reader.readLine())) {
            //2.切割
            String[] fields = line.split("\t");
            //3.缓存数据到集合
            pdMap.put(fields[0], fields[1]);
        }
        //4.关流
        reader.close();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //1.读取一行数据
        String str = value.toString();
        //2.切割
        String[] fields = str.split("\t");
        //3.获取产品id
        String pid = fields[1];
        //4.从pdmap中获取产品的名称
        String pdName = pdMap.get(pid);
        //5.拼接
        str = str + "\t" + pdName;
        //6.封装key
        k.set(str);
        //7.写出
        context.write(k, NullWritable.get());
    }
}
