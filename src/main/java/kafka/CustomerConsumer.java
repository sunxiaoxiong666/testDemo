package kafka;

/*
 * @author: sunxiaoxiong
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

//消费者api
public class CustomerConsumer {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        Properties prop = new Properties();
        //定义kafka服务器的地址，不需要将所有的broker指定上
        prop.put("bootstrap_servers", "hadoop102:9092");
        //制定consumer的group
        prop.put("group.id", "test");
        // 是否自动确认offset
        prop.put("enable.auto.commit", "true");
        // 自动确认offset的时间间隔
        prop.put("auto.commit.interval.ms", "1000");
        // key的反序列化类
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value的反序列化类
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //创建consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
        //消费者订阅的topic，可以同时订阅多个
        consumer.subscribe(Arrays.asList("first", "second", "third"));
        //消费消息
        while (true) {
            //读取数据，超时时间为100毫秒,读取的为一堆数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.offset() + "------" + record.key() + "---" + record.value());
            }
        }

    }
}
