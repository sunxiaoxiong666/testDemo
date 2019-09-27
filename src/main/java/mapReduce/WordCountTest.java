package mapReduce;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WordCountTest {
    /**
     * 我定义一个内部类MyMapper继承Mapper类
     * 泛型解释：LongWritable是大数据里的类型对应java中的Long类型
     * Text对应java里的String类型，所以Mapper泛型前2个就是LongWritable, Text
     * 逻辑解释：由于我们做的是单词计数，文件中的单词是下面2行
     * hello  you
     * hello  me
     * 所以 ，根据上面
     * 步骤1.1，则   <k1,v1>是<0, hello	you>,<10,hello	me> 形式
     * 文件的读取原则：<每行起始字节数，行内容>，所以第一行起始字节是0，内容是hello you
     * 第二行起始字节是10，内容是hello me，从而得出k1,v1
     * 步骤1.2：如果我们要实现计数，我们可以把上面的形式通过下面的map函数转换成这样
     * <k2,v2>--->  <hello,1><you,1><hello,1><me,1>
     * 于是Mapper泛型后2个就是Text，LongWritable
     * 可以理解泛型前2个为输入的map类型，后2个为输出的map类型
     */
    public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        //定义一个k2,v2
        Text k2 = new Text();
        LongWritable v2 = new LongWritable();

        //下面的key就是从文件中读取的k1，value就是v1，map函数就是在执行步骤1.2
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] words = value.toString().split(" ");
            for (String word : words) {
                //word表示每一行中的每个单词，即k2
                k2.set(word);
                // 没有排序分组前每个单词都是1个，因为是long类型所以加1L
                v2.set(1L);
                context.write(k2, v2);//写出
            }
        }
    }

    //步骤1.3:对输出的所有的k2、v2进行分区去执行MapperTask
    //步骤1.4：shuffle-排序后的结果是<hello,1><hello,1><me,1><you,1>
    //        分组后的结果是<hello,{1,1}><me,{1}><you,{1}>
    //1.1,1.3和1.4,1.5是hadoop自动帮我们做的，我们做的就是上面写的map函数的输出逻辑

    /**
     * 下面这个MyReducer函数是输出<k3,v3>的函数，逻辑要我们自己写。
     * 传入的参数是上面得到的<hello,{1,1}><me,{1}><you,{1}>
     * 把这些map分给不同的ReducerTask去完成最后
     * 输出为<k3,v3>是<hello, 2>,<me, 1>,<you, 1>
     */
    public static class MyReduce extends Reducer<Text, LongWritable, Text, LongWritable> {
        LongWritable v3 = new LongWritable();

        //传入的数据形式为<hello,{1,1}>,v的值是个集合，所以这里是Iterable<LongWritable>
        @Override
        protected void reduce(Text k2, Iterable<LongWritable> v2s, Context context) throws IOException, InterruptedException {
            long count = 0L;
            for (LongWritable v2 : v2s) {
                count += v2.get();
            }
            v3.set(count);
            //k2就是k3，都是一个单词
            context.write(k2, v3);
        }
    }

    public static void deleteOutDir(Configuration conf, String OUT_DIR) throws URISyntaxException, IOException {
        FileSystem fs = FileSystem.get(new URI(OUT_DIR), conf);
        if (fs.exists(new Path(OUT_DIR))) {
            fs.delete(new Path(OUT_DIR), true);
        }
    }

    //将map 和reduce结合起来
    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
        //加载驱动
       /* 运行mapreduce程序前都要初始化Configuration，该类主要是读取mapreduce系统配置信息，这些信息包括hdfs还有mapreduce，
        也就是安装hadoop时候的配置文件例如：core-site.xml、hdfs-site.xml和mapred-site.xml等等文件里的信息，有些童鞋不理解为啥要这么做，
        这个是没有深入思考mapreduce计算框架造成，我们程序员开发mapreduce时候只是在填空，在map函数和reduce函数里编写实际进行的业务逻辑，
        其它的工作都是交给mapreduce框架自己操作的，但是至少我们要告诉它怎么操作啊，比如hdfs在哪里啊，mapreduce的jobstracker在哪里啊，
        而这些信息就在conf包下的配置文件里。*/
        Configuration conf = new Configuration();
        //获取job，告知他需要加载哪个类
        Job job = Job.getInstance(conf, WordCountTest.class.getSimpleName());
        //如果程序打成jar包在hadoop中运行，则必须要做这个设置
        job.setJarByClass(WordCountTest.class);
        //获取文件数据
        FileInputFormat.setInputPaths(job, new Path("hdfs://master1:9000/xiong_test2/a2.txt"));
        //通过TextInPutFormat把读到的数据处理成<k1,v1>形式
        job.setInputFormatClass(TextInputFormat.class);
        //job中加入mapper，同时MyMapper类接受<k1，v1>作为参数传递给map函数进行数据处理
        job.setMapperClass(MyMapper.class);
        //设置map输出<k2,v2>的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        //job中加入reducer，reducer自动接收处理好的map数据
        job.setReducerClass(MyReduce.class);
        //设置reduce最终输出<k3,v3>的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        //设置输出目录文件
        String OUT_DIR = "hdfs://master1:9000//xiong_test2/out2";
        FileOutputFormat.setOutputPath(job, new Path(OUT_DIR));
        job.setOutputFormatClass(TextOutputFormat.class);
        //如果这个文件存在则删除，如果不删除会报错
        deleteOutDir(conf, OUT_DIR);
        //把处理好的<k3,v3>的数据写入文件
        job.waitForCompletion(true);
    }
}
