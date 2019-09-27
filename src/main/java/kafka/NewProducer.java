package kafka;

/*
 * @author: sunxiaoxiong
 */

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

//创建生产者
public class NewProducer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        //kafka服务端的主机名和端口号
        prop.put("bootstrap.servers", "hadoop102:9092");
        //等待所有副本节点的应答
        prop.put("acks", "all");
        //消息发送最大尝试次数
        prop.put("retries", 0);
        //请求延时,单位毫秒
        prop.put("linger.ms", 1);
        //一批消息处理的大小,字节
        prop.put("batch.size", 16384);
        //发送缓冲区的大小
        prop.put("buffer.memory", 33554432);
        // key序列化
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //实例化
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
        //发送消息
        for (int i = 0; i < 50; i++) {
            producer.send(new ProducerRecord<String, String>("first", Integer.toString(i), "hello kafka" + i));
        }
        //关闭资源
        producer.close();
    }
}
