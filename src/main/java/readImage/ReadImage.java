package readImage;

/*
 * @author: sunxiaoxiong
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.*;
import java.sql.*;

public class ReadImage {
    private String className = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://123.59.198.88:3312/tmdatabase";
    private String user = "root";
    private String password = "tdcredit2019";
    /*private String url = "jdbc:mysql://123.59.198.72:3317/fin_meta";
    private String user = "root";
    private String password = "tdcredit2019";*/
    private Connection conn = null;

    @Before
    public void before() throws Exception {
        Class.forName(className);
        conn = DriverManager.getConnection(url, user, password);
    }

    @After
    public void after() throws Exception {
        conn.close();
    }

    @Test
    public void test() throws Exception {//将图片存到数据库
        File file = new File("E:\\图片\\辉夜姬.jpg");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = fis.read(buf)) != -1) {//汇总字节流到内存
            baos.write(buf, 0, len);
        }
        baos.close();
        fis.close();
        byte[] bytes = baos.toByteArray();//从内存取出字节流数组
        Blob pic = conn.createBlob();
        pic.setBytes(1, bytes);//把字节流设置给blob类
        String sql = "insert into images(image) values (?)";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setBlob(1, pic);
        ppst.execute();
        ppst.close();
    }

    @Test
    public void test2() throws Exception {//从数据库取出图片成图片
        long date1 = System.currentTimeMillis();
        String sql = "select * from tm_pic_small limit 10000";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        System.out.println("查询完成，查询耗时：" + (System.currentTimeMillis() - date1));
        int i = 1;
        while (rs.next()) {
            System.out.println("保存了" + i + "张");
            i++;
            Blob pic = rs.getBlob("pic_image");
            InputStream is = pic.getBinaryStream();
            // InputStream is = rs.getBinaryStream(1);
            int len = -1;
            byte[] buf = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            is.close();
            baos.close();
            byte[] bytes = baos.toByteArray();
            File file = new File("/opt/xinhua_test/xiaoxiong_test/image" + rs.getInt("id") + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        }
        long date2 = System.currentTimeMillis();
        System.out.println("耗时：" + (date2 - date1));
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ReadImage.class);
        System.out.println(result.wasSuccessful() ? "运行成功" : "运行失败");
        System.out.println(result.wasSuccessful() ? 0 : 1);
    }
}
