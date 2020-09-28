package hbase;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/10 16:24
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
 * hbase的api使用
 * */
public class HbaseApi {

    //首先需要获取configuration对象
    public static Configuration conf;

    static {
        //使用HBaseConfiguration的单例方法实例化
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.126.102");//设置zookeeper
        conf.set("hbase.zookeeper.property.clientPort", "2181");//zookeeper的端口
    }

    public static void main(String[] args) throws Exception {
//        boolean aa = isexist("stuedent");
//        System.out.println(aa);
        createTble("staff","info1","info2");
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
        //判断表是否存在
        if (admin.tableExists(tableName)) {
            //此处要先调用这个方法，否则不能删除表
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        } else {
            System.out.println(tableName + "表不存在。");
        }
    }

    //4.向表中插入数据
    public static void addRowDate(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
        //创建HTable对象
        // HTable hTable = new HTable(conf, tableName);//此方法过时
        Connection connection = ConnectionFactory.createConnection(conf);
        HTable hTable = (HTable) connection.getTable(TableName.valueOf(tableName));
        //创建put对象
        Put put = new Put(Bytes.toBytes(rowKey));
        //向put对象中组装数据
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        hTable.put(put);
        hTable.close();
        System.out.println("插入数据成功。");
    }

    //5.删除多行数据
    public static void deleteRowDate(String tableName, String... rowKeys) throws IOException {
        HTable hTable = new HTable(conf, tableName);
        // Connection connection = ConnectionFactory.createConnection(conf);
        // HTable table = (HTable)connection.getTable(TableName.valueOf(tableName));
        List<Delete> list = new ArrayList<Delete>();
        for (String rowKey : rowKeys) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            list.add(delete);
        }
        hTable.delete(list);
        hTable.close();
    }

    //6，得到所有数据
    public static void getAllDate(String tableName) throws IOException {
        HTable hTable = new HTable(conf, tableName);
        //获取扫描region的对象
        Scan scan = new Scan();
        //使用htable得到resultScanner实现类的对象
        ResultScanner scanner = hTable.getScanner(scan);
        for (Result result : scanner) {
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                //得到rowkey
                System.out.println("行键：" + Bytes.toString(CellUtil.cloneRow(cell)));
                //得到列族
                System.out.println("列族：" + Bytes.toString(CellUtil.cloneFamily(cell)));
                //得到列
                System.out.println("列：" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值：" + Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
    }

    //7.得到某一行的数据
    public static void getRow(String tableName, String rowKey) throws IOException {
        HTable hTable = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        get.setMaxVersions();//显示所有版本
        // get.setTimeStamp();//显示指定时间戳的版本
        Result result = hTable.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println("行键：" + Bytes.toString(result.getRow()));
            System.out.println("列族：" + Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.println("列：" + Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println("值：" + Bytes.toString(CellUtil.cloneValue(cell)));
            System.out.println("时间戳：" + cell.getTimestamp());
        }
    }

    //8.获取某一行指定“列族：列”的数据
    public static void getRowQualifier(String tableName, String rowKey, String columnFamily, String column) throws IOException {
        HTable hTable = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        Result result = hTable.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println("行键：" + Bytes.toString(result.getRow()));
            System.out.println("列族：" + Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.println("列：" + Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println("值：" + Bytes.toString(CellUtil.cloneValue(cell)));
        }
    }
}
