package mapReduce.webLog2;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class LogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    Text k = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取一行数据
        String line = value.toString();
        //2.清洗数据,要单独写一个方法
        LogBean bean = parse(line, context);
        if (!bean.isValid()) {
            return;
        }
        k.set(bean.toString());
        //3.输出
        context.write(k, NullWritable.get());
    }

    //清洗数据
    private LogBean parse(String line, Context context) {
        LogBean logBean = new LogBean();
        //1.分割数据
        String[] fields = line.split(" ");
        //2.进行判断，将符合条件的数据封装到logbean中
        if (fields.length > 3) {
            //封装对象
            logBean.setRemote_addr(fields[0]);
            logBean.setRemote_user(fields[1]);
            logBean.setTime_local(fields[3].substring(1));
            logBean.setRequest(fields[6]);
            logBean.setStatus(fields[8]);
            logBean.setBody_bytes_sent(fields[9]);
            logBean.setHttp_referer(fields[10]);

            if (fields.length > 4) {
                logBean.setHttp_user_agent(fields[11] + " " + fields[12]);
            } else {
                logBean.setHttp_user_agent(fields[11]);
            }
            //大于400，http报错
            if (Integer.parseInt(logBean.getStatus()) >= 400) {
                logBean.setValid(false);
            }
        } else {
            logBean.setValid(false);
        }
        return logBean;
    }
}
