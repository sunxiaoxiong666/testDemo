package kafka.stream;

/*
 * @author: sunxiaoxiong
 */

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

//创建主类
public class Application {
    public static void main(String[] args) {
        //定义输入的topic
        String from = "first";
        // 定义输出的topic
        String to = "second";

        //设置参数
        Properties prop = new Properties();
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG, "logFilter");
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");

        StreamsConfig config = new StreamsConfig(prop);
        //构建拓扑
        TopologyBuilder builder = new TopologyBuilder();
        //1.添加数据来源
        builder.addSource("source", from)
                .addProcessor("process", new ProcessorSupplier<byte[], byte[]>() {
                    public Processor<byte[], byte[]> get() {
                        //2.处理业务逻辑
                        return new LogProcessor();

                    }
                }, "source")
                //3.将数据发给第二个topic
                .addSink("sink", to, "process");
        //创建kafka stream
        KafkaStreams kafkaStreams = new KafkaStreams(builder, prop);
        kafkaStreams.start();
    }
}
