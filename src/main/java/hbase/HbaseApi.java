package hbase;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/10 16:24
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;


/*
 * hbase的api使用
 * */
public class HbaseApi {

    //首先需要获取configuration对象
    public static Configuration conf;

    static {
        //使用HBaseConfiguration的单例方法实例化
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.216.20");//设置zookeeper
        conf.set("hbase.zookeeper.property.clientPort", "2181");//zookeeper的端口
    }

    public static void main(String[] args) throws Exception {
        isexist("aa");
    }

    //1.判断表是否存在
    public static boolean isexist(String tableName) throws Exception {
        //在hbase中管理和访问表需要先创建HBaseAdmin对象
        Connection connection = ConnectionFactory.createConnection(conf);
        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        HBaseAdmin admin2 = new HBaseAdmin(conf);//过时方法
        return admin.tableExists(tableName);
    }

    //2.创建表
    public static void createTble(String tableName, String... columnFamily) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        HBaseAdmin admin1 = (HBaseAdmin) connection.getAdmin();
        //判断表是否存在
        if (admin1.tableExists(tableName)) {
            System.out.println(tableName + "表已经存在");
        } else {
            //创建表属性对象，表名需要转字节
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            //创建多个列族
            for (String str : columnFamily) {
                hTableDescriptor.addFamily(new HColumnDescriptor(str));
            }
            //根据对表的配置，创建表
            admin1.createTable(hTableDescriptor);
            System.out.println(tableName + "表创建成功。");
        }
    }

    //3.删除表
    public static void dropTble(String tableName) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        
    }


}
