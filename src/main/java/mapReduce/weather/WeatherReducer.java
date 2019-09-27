package mapReduce.weather;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class WeatherReducer extends Reducer<Weather, Text, Weather, Text> {
    @Override
    protected void reduce(Weather key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        System.out.println(key.toString() + "=======");
        Iterator<Text> itera = values.iterator();
        int date = -1;
        while (itera.hasNext()) {
            Text value = itera.next();
            if (date == -1) {
                //此时values的数据是已经从高到低排好顺序的
                //将你第一个记录，最高温度记录写出去
                System.out.println("第一" + key.getDate() +"value"+ value);
                context.write(key, value);
                date = key.getDate();
            } else {
                if (key.getDate() != date) {
                    //如果当前日期和第一条记录的日期不同，则为第二条温度记录，写完后退出
                    System.out.println("第二" + key.getDate() +"value"+ value);
                    context.write(key, value);
                    break;
                } else {
                    //nothing
                }
            }
        }
    }
}
