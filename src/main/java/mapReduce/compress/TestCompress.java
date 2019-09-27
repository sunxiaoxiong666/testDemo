package mapReduce.compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
 * @author: sunxiaoxiong
 */
/*
 * 数据流的压缩和解压缩
 * */
public class TestCompress {
    public static void main(String[] args) throws Exception {
        //压缩
        // compress("d:/hello.txt", "org.apache.hadoop.io.compress.BZip2Codec");
        // compress("d:/hello.txt", "org.apache.hadoop.io.compress.GzipCodec");
        // compress("d:/hello.txt", "org.apache.hadoop.io.compress.DefaultCodec");
        //解压缩
        // deCompress("d:/hello.txt.bz2");
        // deCompress("d:/hello.txt.gz");
        deCompress("d:/hello.txt.deflate");
    }

    //压缩
    public static void compress(String filename, String method) throws Exception {
        //1.获取输入流，输入为正常的文件
        FileInputStream fis = new FileInputStream(new File(filename));

        Class codesClass = Class.forName(method);
        CompressionCodec codes = (CompressionCodec) ReflectionUtils.newInstance(codesClass, new Configuration());
        //2.获取压缩输出流，输出为压缩文件
        FileOutputStream fos = new FileOutputStream(new File(filename + codes.getDefaultExtension()));
        CompressionOutputStream cos = codes.createOutputStream(fos);
        //3.流的拷贝
        IOUtils.copyBytes(fis, cos, 1024 * 1024 * 5, false);
        //4.关闭资源
        fis.close();
        //注意下面两个流的关闭是有顺序的，否则会报错
        cos.close();
        fos.close();
    }

    //解压缩
    public static void deCompress(String filename) throws Exception {
        //1.校验是否能解压缩
        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());
        CompressionCodec codec = factory.getCodec(new Path(filename));
        if (codec == null) {
            System.out.println(filename + "文件不能解压缩");
            return;
        }

        //2.获取压缩输入流，输入的文件为压缩格式
        CompressionInputStream cis = codec.createInputStream(new FileInputStream(new File(filename)));
        //3.获取输出流，输出正常的文件
        FileOutputStream fos = new FileOutputStream(new File(filename + ".decoded"));
        //4.流的拷贝
        IOUtils.copyBytes(cis, fos, 1024 * 1024 * 5, false);
        //5.关闭资源
        fos.close();
        cis.close();
    }
}
