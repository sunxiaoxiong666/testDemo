package kafka;

/*
 * @author: sunxiaoxiong
 */

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

//统计发送消息成功和发送失败消息数，并在producer关闭时打印这两个计数器
public class CounterInterceptor implements ProducerInterceptor<String, String> {

    private int errCount = 0;
    private int successCount = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        //统计成功和失败的次数
        if (exception == null) {
            successCount++;
        } else {
            errCount++;
        }
    }

    @Override
    public void close() {
        //输出结果
        System.out.println("成功的次数" + successCount);
        System.out.println("失败的次数" + errCount);

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
