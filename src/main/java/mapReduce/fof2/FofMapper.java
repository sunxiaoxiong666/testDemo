package mapReduce.fof2;

/*
 * @author: sunxiaoxiong
 */

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FofMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] strs = StringUtils.split(value.toString());
        for (int i = 0; i < strs.length; i++) {
            for (int j = i + 1; j < strs.length; j++) {
                if (i == 0) {
                    context.write(new Text(getNames(strs[i], strs[j])), new IntWritable(0));
                } else {
                    context.write(new Text(getNames(strs[i], strs[j])), new IntWritable(1));
                }
            }
        }
    }

    private String getNames(String str1, String str2) {
        if (str1.compareTo(str2) > 0) {
            return str1 + "-" + str2;
        }
        return str2 + "-" + str1;
    }
}
