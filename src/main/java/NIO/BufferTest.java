package NIO;

/*
 * @author: sunxiaoxiong
 */

import org.junit.Test;

import java.nio.ByteBuffer;

public class BufferTest {
    @Test
    public void test1() {
        String str = "abcdef";
        //创建非直接缓冲区，将缓冲区建在jvm中
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        //将缓冲区的界限设置为当前位置，并将当前位置充值为0
        buf.flip();

        //返回buffer的界限(limit)的位置
        int limit = buf.limit();
        System.out.println("buffer的界限位置为：" + limit);
        byte[] by = new byte[limit];
        buf.get(by, 0, 2);
        System.out.println(new String(by, 0, 2));
        System.out.println(buf.position());

        //标记
        buf.mark();
        buf.get(by, 2, 2);
        System.out.println(new String(by, 2, 2));
        System.out.println(buf.position() + "---------");

        //回复到mark的位置
//        buf.reset();
        System.out.println(buf.position());
        buf.get(by, 4, 2);
        System.out.println(new String(by, 0, 2) + "++++++++");
        System.out.println(buf.position());

        System.out.println("++++++++++++++++++");
        System.out.println(new String(by, 0, 4));

    }

    @Test
    public void test2() {
        String str = "abcdef";
        if (!"null".equalsIgnoreCase(str)){
            System.out.println("比较");
        }

        //创建指定大小的非直接缓冲区，将缓冲区建立在jvm中
        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("======allocate()======");
        System.out.println("缓冲区的位置为：" + buf.position());
        System.out.println("缓冲区的界限为：" + buf.limit());
        System.out.println("缓冲区的容量为：" + buf.capacity());

        //利用put()在缓冲区中存入数据
        buf.put(str.getBytes());
        System.out.println("========put()========");
        System.out.println("放入数据后的缓冲区位置为：" + buf.position());
        System.out.println("放入数据后的缓冲区界限为：" + buf.limit());
        System.out.println("放入数据后的缓冲区容量为：" + buf.capacity());

        //利用flip()切换读取数据模式
        buf.flip();
        System.out.println("======flip()======");
        System.out.println("切换读取数据模式后的缓冲区的位置为：" + buf.position());
        System.out.println("切换读取数据模式后的缓冲区的界限为：" + buf.limit());
        System.out.println("切换读取数据模式后的缓冲区的容量为：" + buf.capacity());

        //利用get()读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println("======get()======");
        System.out.println(new String(dst, 0, buf.limit()));
        System.out.println("读取数据后的缓冲区的位置为：" + buf.position());
        System.out.println("读取数据后的缓冲区的界限为：" + buf.limit());
        System.out.println("读取数据后的缓冲区的容量为：" + buf.capacity());

        //rewind() 可重复读
        buf.rewind();
        System.out.println("=========rewind()========");
        System.out.println("重复读后的缓冲区的位置：" + buf.position());
        System.out.println("重复读后的缓冲区的界限：" + buf.limit());
        System.out.println("重复读后的缓冲区的容量：" + buf.capacity());

        //clear() 清空缓冲区，但是缓冲区中的数据仍然存在，但是出于被遗忘状态
        buf.clear();
        System.out.println("=======clear()==========");
        System.out.println("清空后的位置：" + buf.position());
        System.out.println("清空后的界限：" + buf.limit());
        System.out.println("清空后的容量：" + buf.capacity());

        Integer aa=(Integer)null;
        System.out.println(aa);
    }
}
