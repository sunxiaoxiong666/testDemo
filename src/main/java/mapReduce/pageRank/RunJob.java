/*
package mapReduce.pageRank;

*/
/*
 * @author: sunxiaoxiong
 *//*


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class RunJob {

    public static enum Mycounter {
        my
    }

    public static void main(String[] args) {

        Configuration conf = new Configuration(true);
        //跨平台提交作业
        conf.set("mapreduce.app-submission.corss-paltform", "true");
        //切换分布式到本地单进程模拟运行
        conf.set("mapreduce.framework.name", "local");
        conf.set("mapreduce.cluster.local.dir", "G:\\mapreduce.cluster.local.dir");
        double d = 0.0000001;
        int i = 0;
        while (true) {
            i++;
            try {
                conf.setInt("runCount", i);
                FileSystem fs = FileSystem.get(conf);
                //创建job对象
                Job job = Job.getInstance(conf);
                //设置程序主入口
                job.setJarByClass(RunJob.class);
                //设置作业的名称
                job.setJobName("pr" + i);
                //设置mapper和reducer
                job.setMapperClass(PageRankMapper.class);
                job.setReducerClass(PangeRankReducer.class);
                //设置输出key，value的数据类型
                job.setMapOutputKeyClass(Text.class);
                job.setMapOutputValueClass(Text.class);
                //A\tB\tD           key:A   value:B\tD
                //使用了新的输入格式化类，读取一行数据，按照\t将数据分为key，value，只按照第一个\t分隔
                job.setInputFormatClass(KeyValueTextInputFormat.class);

                Path inputPath = new Path("/xiong_test2/pagerank/input/");
                if (i > 1) {
                    inputPath = new Path("/xiong_test2/pagerank/output/pr" + (i - 1));
                }
                FileInputFormat.addInputPath(job, inputPath);
                Path outPath = new Path("/xiong_test2/pagerank/output/pr" + i);
                if ((fs.exists(outPath))) {
                    fs.delete(outPath, true);
                }
                FileOutputFormat.setOutputPath(job, outPath);

                boolean f = job.waitForCompletion(true);
                if (f) {
                    System.out.println("success");
                    //将每个页面的本次PR值减去上次的PR值，求绝对值，在对所有页面的这个值求和
                    long sum = job.getCounters().findCounter(Mycounter.my).getValue();
                    System.out.println(sum);

                    //乘以1000除以4000，相当于除以4，因为有4个页面，这个值要看实际情况而定
                    double avg = sum / 4000.0;
                    if (avg < d) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class PageRankMapper extends Mapper<Text, Text, Text, Text> {
        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            int runCount = context.getConfiguration().getInt("runCount", 1);

        }
    }

}
*/
