package mapReduce.fof2;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FofMapper2 extends Mapper<LongWritable, Text, Fof, IntWritable> {
    private Fof outKey = new Fof();
    private IntWritable outValue = new IntWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //hadoop-cat 2
        String str = value.toString();
        if ("".equals(str.trim())) {
            return;
        }

        //{"hadoop-cat","2"}
        String[] strs1 = str.split("\t");
        //{"hadoop","cat"}
        String[] strs2 = strs1[0].split("-");
        Integer number = Integer.parseInt(strs1[1]);
        outKey.setAname(strs2[0]);
        outKey.setBname(strs2[1]);
        outKey.setNum(number);
        outValue.set(number);
        context.write(outKey, outValue);
        //颠倒名字顺序在写一遍
        outKey.setAname(strs2[1]);
        outKey.setBname(strs2[0]);
        System.out.println("++++++++++++++++++++");
        context.write(outKey, outValue);
    }
}
