package mapReduce.weather;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherMapper extends Mapper<LongWritable, Text, Weather, Text> {

    private Weather outKey = new Weather();
    // private Text outValue = new Text();

    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //1949-10-01  14:21:02   34c
        String str = value.toString();
        //{"1949-10-01 14:21:02","34c"}
        String[] strs1 = str.split("\t");
        //{"1949-10-01","14:21:02"}
        String[] strs2 = strs1[0].split(" ");

        try {
            //得到温度的数字
            String tempstr = strs1[1].substring(0, strs1[1].length() - 1);
            Date date = sdFormat.parse(strs2[0]);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            outKey.setYear(cal.get(Calendar.YEAR));
            outKey.setMonth(cal.get(Calendar.MONTH));
            outKey.setDate(cal.get(Calendar.DATE));
            outKey.setTemperature(Integer.parseInt(tempstr));

            // outValue.set(strs1[1]);
            // context.write(outKey,outValue);
            System.out.println("map的key" + outKey.toString() + "value" + value);
            context.write(outKey, value);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
