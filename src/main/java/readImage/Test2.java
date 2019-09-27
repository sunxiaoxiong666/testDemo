/*
package readImage;

import java.io.*;
import java.sql.*;

*/
/**
 * 测试读取数据库LongBlob格式数据（图片数据）存储到本地不同文件夹中（5000个图片一个文件夹）
 * <p>
 * Author: Fss
 * Date: 2019/6/27 14:38
 * Version: 1.0.0
 * 下面是本地测试版本的代码
 *//*




public class Test2 {

    //1.连接数据库//变量放外面
    static Connection conn=null;
    PreparedStatement ps=null;
    static String outputPath2 = ""; //读取后的图片存储路径（测试用）
    static String new_outputPath = "/data1/fei_test/trademark_picture/id2imagepath/"; //id和图片地址对应文件地址

    public Test2 () {
        try {
            String url="jdbc:mysql://123.59.198.88:3312/tmdatabase";
            Class.forName("com.mysql.jdbc.Driver");
            if(null==conn){
                conn= DriverManager.getConnection(url,"root","tdcredit2019");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException ce) {
            ce.printStackTrace();
        }
    }

    //2.从数据库中读取图片数据
    public void Read() throws SQLException,IOException{
        int sum = 0; //数据表tm_pic_small总条数
        String sq="select count(*) as sum from tm_pic_small";
        ResultSet r=getQuery(sq);
        sum = r.getInt("sum");
        int num = 0; //计数器
        int filePath = 1; // 文件夹目录
        String id = ""; //记录数据ID
        int queryNum = 10000; //分页查询每一页的条数

//            String outputPath = "/opt/fei_test/image/"; //读取后的图片存储路径（生产用）
        int pageNum = sum/queryNum + 1; //循环次数
//            System.out.println("循环次数：" + pageNum);

        PreparedStatement ps;
        ResultSet rs;
        File ff;
        FileWriter writer=null;
        for(int i=1; i<pageNum; i++){
            rs=getQuery("select id,pic_image from tm_pic_small  where length(pic_image)>10 limit "+(i-1)*queryNum+","+queryNum+";");
            ff = new File(new_outputPath);
            if  (!ff.exists() && !ff.isDirectory()){
                System.out.println(outputPath2 + "id和图片地址文件夹不存在!!!");
                ff .mkdir();
            }
            if(num%10000==0){
                outputPath2 = "/data1/fei_test/trademark_picture/"+filePath+"/";
                ff = new File(outputPath2);
                if  (!ff.exists() && !ff.isDirectory()){
                    System.out.println(outputPath2 + "图片文件夹不存在!!!");
                    ff .mkdir();
                }
                filePath++;
            }
            StringBuffer sb = new StringBuffer();
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
                ff = new File(outputPath2+id+".jpg");
//                    sb.append(id+","+outputPath+id+".jpg"+"\n"); //windows中使用的路径
                sb.append(id+","+outputPath2+id+".jpg"+"\n"); //88服务器中使用的路径
                FileOutputStream out=new FileOutputStream(ff);
                out.write(image, 0, image.length);
                out.close();
                System.out.println("▷▶▷▶▷▶ 图片" + (++num) +"  成功获取!!!");
            }
            rs.close();
            try {
                writer = new FileWriter(new_outputPath+(filePath-1)+".txt");
                writer.write("");//清空原文件内容
                writer.write(sb.toString());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(null!=writer){
                    try {
                        writer.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private ResultSet getQuery(String sql){
        ResultSet rss=null;
        try {
                ps=conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY ,ResultSet.CONCUR_READ_ONLY);
                ps.setFetchSize(Integer.MIN_VALUE);
                ps.setFetchDirection(ResultSet.FETCH_REVERSE);
                rss=ps.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if(null!=ps){
                    ps.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return rss;
    }

    public static void main(String[] args) {
        Test2 picture=new Test2 ();
        try {
            picture.Read();//读取图片
        }catch (IOException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
*/


/**
 * 下面是本地测试版本的代码
 */

package readImage;

import java.io.*;
import java.sql.*;

/**
 * 测试读取数据库LongBlob格式数据（图片数据）存储到本地不同文件夹中（5000个图片一个文件夹）
 *
 * Author: Fss
 * Date: 2019/6/27 14:38
 * Version: 1.0.0*/


public class Test2 {


    //1.连接数据库
    static Connection conn = null;
    PreparedStatement ps = null;
    //static String outputPath2 = "/data1/fei_test/trademark_picture/"; //读取后的图片存储路径（测试用）
    //static String new_outputPath = outputPath2+"id2imagepath/"; //id和图片地址对应文件地址
    static String outputPath2 = ""; //读取后的图片存储路径（测试用）
    static String new_outputPath = "/E:/7_1/id2imagepath/"; //id和图片地址对应文件地址

    public Test2() {
        try {
            String url = "jdbc:mysql://123.59.198.88:3312/tmdatabase";
            Class.forName("com.mysql.jdbc.Driver");
            if (null == conn) {
                conn = DriverManager.getConnection(url, "root", "tdcredit2019");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
    }

    //2.从数据库中读取图片数据
    public void Read() throws SQLException, IOException {
        int sum = 0; //数据表tm_pic_small总条数
        String sq = "select count(*) as sum from tm_pic_small";

        ResultSet r = getQuery(sq);
        while (r.next()) {
            sum = r.getInt("sum");
        }
        int num = 0; //计数器
        int filePath = 1; // 文件夹目录
        String id = ""; //记录数据ID
        int queryNum = 10000; //分页查询每一页的条数

//            String outputPath = "/opt/fei_test/image/"; //读取后的图片存储路径（生产用）
        int pageNum = sum / queryNum + 1; //循环次数
//            System.out.println("循环次数：" + pageNum);
        //变量放外面
        PreparedStatement ps;
        ResultSet rs;
        File ff;
        FileWriter writer = null;
        Blob bl =null;
        for (int i = 1; i < pageNum; i++) {
            rs = getQuery("select id,pic_image from tm_pic_small  where length(pic_image)>10 limit " + (i - 1) * queryNum + "," + queryNum + ";");
            ff = new File(new_outputPath);
            if (!ff.exists() && !ff.isDirectory()) {
                System.out.println(outputPath2 + "id和图片地址文件夹不存在");
                ff.mkdir();
            }
            if (num % 10000 == 0) {
                outputPath2 = "/E:/7_1/" + filePath + "/";
                ff = new File(outputPath2);
                if (!ff.exists() && !ff.isDirectory()) {
                    System.out.println(outputPath2 + "图片文件夹不存在");
                    ff.mkdir();
                }
                filePath++;
            }
            StringBuffer sb = new StringBuffer();
            while (rs.next()) {
                //获取photo字段的图片数据
                id = rs.getString("id");
                Blob blob = rs.getBlob("pic_image");
                InputStream reader = blob.getBinaryStream();
                int size = 0;
                //获取图片大小
                size = (int) blob.length();
                //创建和图片大小的数组
                byte[] image = new byte[size];
                //将数据存储在字节数组b中
                reader.read(image);
                reader.close();
                //从数据库获取图片保存的位置
                ff = new File(outputPath2 + id + ".jpg");
                sb.append(id + "," + outputPath2 + id + ".jpg" + "\n"); //88服务器中使用的路径
                FileOutputStream out = new FileOutputStream(ff);
                out.write(image, 0, image.length);
                out.close();
                System.out.println("▷▶▷▶▷▶ 图片" + (++num) + "  成功获取!!!");
            }
            rs.close();
            try {
                writer = new FileWriter(new_outputPath + (filePath - 1) + ".txt");
                writer.write("");//清空原文件内容
                writer.write(sb.toString());//此处的sb对象用完后需要清空，sb.delete(0,sb.length);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != writer) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private ResultSet getQuery(String sql) {
        ResultSet rss = null;
        try {
            ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setFetchSize(Integer.MIN_VALUE);
            ps.setFetchDirection(ResultSet.FETCH_REVERSE);
            rss = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rss;
    }

    public static void main(String[] args) {
        Test2 picture = new Test2();
        try {
            picture.Read();//读取图片
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
