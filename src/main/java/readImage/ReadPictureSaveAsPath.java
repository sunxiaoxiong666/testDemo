package readImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * 读取数据库中的图片，将图片转换保存为路径
 * Author: Fss
 * Date: 2019/6/27 9:41
 * Version: 1.0.0
 */
public class ReadPictureSaveAsPath {
    //1.连接数据库
    Connection conn=null;
    public ReadPictureSaveAsPath () {
        try {
            String url="jdbc:mysql://123.59.198.88:3312/tmdatabase";
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection(url,"root","tdcredit2019");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException ce) {
            ce.printStackTrace();
        }
    }

    //2.从数据库中读取图片数据
    public void Read() {
        try {
            int sum = 0; //数据表tm_pic_small总条数
            String sq="select count(*) as sum from tm_pic_small";
            PreparedStatement p=conn.prepareStatement(sq);
            ResultSet r=p.executeQuery();
            while(r.next()) {
                sum = r.getInt("sum");
            }
            System.out.println("总数；" + sum);
            int num = 1; //计数器
            String id = ""; //记录数据ID
            int queryNum = 500; //分页查询每一页的条数
//            String outputPath = "E:/6_26/"; //读取后的图片存储路径（测试用）
            String outputPath = "/opt/fei_test/image/"; //读取后的图片存储路径（生产用）

            for(int i=1; i<sum; i++){

                String sql="select id,pic_image from tm_pic_small  where length(pic_image)>10 limit "+(i-1)*queryNum+","+queryNum+";";
                PreparedStatement ps=conn.prepareStatement(sql);
                ResultSet rs=ps.executeQuery();
                while(rs.next()) {
                    //获取photo字段的图片数据
                    id = rs.getString("id");
                    Blob blob=rs.getBlob("pic_image");
                    InputStream reader=blob.getBinaryStream();
                    int size = 0;
                    //获取图片大小
                    size = (int)blob.length();
                    //创建和图片大小的数组
                    byte[] image=new byte[size];
                    //将数据存储在字节数组b中
                    reader.read(image);
                    reader.close();
                    //从数据库获取图片保存的位置
                    File f=new File(outputPath+id+".jpg");
                    FileOutputStream out=new FileOutputStream(f);
                    out.write(image, 0, image.length);
                    out.close();
                    System.out.println("▷▶▷▶▷▶ 图片" + (num++) +"  成功获取!!!");
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadPictureSaveAsPath  picture=new ReadPictureSaveAsPath ();
        picture.Read();//读取图片
    }
}
