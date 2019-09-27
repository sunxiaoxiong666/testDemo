package mapReduce.fof;

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
        // tom hello hadoop cat
        String[] strs = StringUtils.split(value.toString());
        for (int i = 0; i < strs.length; i++) {
            for (int j = i + 1; j < strs.length; j++) {
                if (i == 0) {
                    /*
                     * <tom-hello, 0>
                     * <tom-hadoop, 0>
                     * <tom-cat, 0>
                     * */
                    context.write(new Text(getNames(strs[i], strs[j])), new IntWritable(0));
                } else {
                    /*
                     * <cat-hadoop, 1>
                     * <hadoop-hello, 1>
                     * <cat-hello, 1>
                     * */
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
