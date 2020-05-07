package kafka.stream;

/*
 * @author: sunxiaoxiong
 */

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

//处理业务逻辑
public class LogProcessor implements Processor<byte[], byte[]> {

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext processorContext) {
        this.context = processorContext;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        String input = new String(value);
        //如果包含“>>>”,则只保留后面的内容
        if (input.contains(">>>")) {
            input = input.split(">>>")[1].trim();
            //输出到下一个topic
            context.forward(key, input.getBytes());
        } else {
            context.forward(key, value);
        }


    }

    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }
}
