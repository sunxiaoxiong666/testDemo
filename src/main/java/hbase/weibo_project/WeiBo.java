package hbase.weibo_project;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/17 14:21
 */
/*
1.1、需求分析
1) 微博内容的浏览，数据库表设计
2) 用户社交体现：关注用户，取关用户
3) 拉取关注的人的微博内容
1.2、代码实现
代码设计总览：
1) 创建命名空间以及表名的定义
2) 创建微博内容表
3) 创建用户关系表
4) 创建用户微博内容接收邮件表
5) 发布微博内容
6) 添加关注用户
7) 移除（取关）用户
8) 获取关注的人的微博内容
9)
* */
public class WeiBo {

    //1.创建命名空间和表名的定义
    //获取配置conf
    private Configuration conf = HBaseConfiguration.create();
    //微博内容表的表名
    private static final byte[] TABLE_CONTENT = Bytes.toBytes("ns_weibo:content");
    //用户关系表的表名
    private static final byte[] TABLE_RELATION = Bytes.toBytes("ns_weibo:relation");
    //微博收件箱表的表名
    private static final byte[] TABLE_INBOX = Bytes.toBytes("ns_weibo:inbox");

    //初始化命名空间
    public void initNameSpace() {
        HBaseAdmin admin = null;
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            admin = (HBaseAdmin) connection.getAdmin();
            //命名空间类似于关系型数据库中的schema，可以想象成文件夹
            NamespaceDescriptor weibo = NamespaceDescriptor.create("ns_weibo")
                    .addConfiguration("creatoor", "xiong")
                    .addConfiguration("create_time", System.currentTimeMillis() + "")
                    .build();
            admin.createNamespace(weibo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * 2.创建微博内容表
     *table_name: ns_weibo:content
     * rowkey: 用户ID+时间戳
     * columnFamily: info
     * columnlabel: 标题，内容，图片URL
     * VERSION: 1个版本
     * */

    public void createTableContent() {
        HBaseAdmin admin = null;
        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = (HBaseAdmin) connection.getAdmin();
            //创建表描述
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_CONTENT));
            //创建列族描述
            HColumnDescriptor infoColumnFamilyDescriptor = new HColumnDescriptor(Bytes.toBytes("info"));
            //设置块缓存
            infoColumnFamilyDescriptor.setBlockCacheEnabled(true);
            //设置块缓存大小
            infoColumnFamilyDescriptor.setBlocksize(2097152);
            //设置压缩方式
            // infoColumnFamilyDescriptor.setCompressionType(Compression.Algorithm.SNAPPY);
            //设置版本确界
            infoColumnFamilyDescriptor.setMaxVersions(1);
            infoColumnFamilyDescriptor.setMinVersions(1);
            hTableDescriptor.addFamily(infoColumnFamilyDescriptor);
            admin.createTable(hTableDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * 3.创建用户关系表
     *table_name: ns_weibo:relation
     * rowkey: 用戶id
     * columnFamily: attends,fans
     * columnlabel: 关注用户id，粉丝用户id
     * columnvalue: 用户id
     * version: 1个版本
     *
     * */

    public void createTableRelation() {
        HBaseAdmin admin = null;
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            admin = (HBaseAdmin) connection.getAdmin();
            //创建表描述
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_RELATION));
            //关注人的列族
            HColumnDescriptor attendsColumn = new HColumnDescriptor(Bytes.toBytes("attends"));
            //设置块缓存
            attendsColumn.setBlockCacheEnabled(true);
            //设置块缓存大小
            attendsColumn.setBlocksize(2097152);
            //设置压缩方式
            // attendsColumn.setCompressionType(Compression.Algorithm.SNAPPY);
            //设置版本确界
            attendsColumn.setMaxVersions(1);
            attendsColumn.setMinVersions(1);

            //粉丝列族
            HColumnDescriptor fansColumn = new HColumnDescriptor(Bytes.toBytes("fans"));
            fansColumn.setBlockCacheEnabled(true);
            fansColumn.setBlocksize(2097152);
            fansColumn.setMaxVersions(1);
            fansColumn.setMinVersions(1);

            hTableDescriptor.addFamily(attendsColumn);
            hTableDescriptor.addFamily(fansColumn);
            admin.createTable(hTableDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * 4.创建微博收件箱表
     * table_name: ns_weibo:inbox
     * rowkey: 用户id
     * columnFamily: info
     * columnLable：用户id_发布微博的人的用户id
     * columnvalue: 关注的人的微博的rowkey
     * version: 1000
     * */
    public void createTableInbox() {
        HBaseAdmin admin = null;
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            admin = (HBaseAdmin) connection.getAdmin();
            //创建表描述
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_INBOX));
            //创建列族的描述
            HColumnDescriptor infoColumn = new HColumnDescriptor(Bytes.toBytes("info"));
            //设置块缓存
            infoColumn.setBlockCacheEnabled(true);
            //设置快缓存大小
            infoColumn.setBlocksize(2097152);
            //设置版本确界
            infoColumn.setMaxVersions(1000);
            infoColumn.setMinVersions(1000);
            hTableDescriptor.addFamily(infoColumn);
            admin.createTable(hTableDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * 5.发布微博
     * a.微博内容表中数据+1
     * b.向微博收件箱表中加入微博的rowkey
     * */

    public void publishContent(String uid, String content) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(conf);
            //a.向微博内容表中添加数据，首先要获得微博内容表描述
            Table contentTable = connection.getTable(TableName.valueOf(TABLE_CONTENT));
            //组装rowkey
            long time = System.currentTimeMillis();
            String rowKey = uid + "_" + time;
            //添加微博内容
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("content"), time, Bytes.toBytes(content));
            contentTable.put(put);

            //b.向微博收件箱表中添加发布的rowkey
            //b.1 查询用户关系表，得到当前用户有哪些粉丝
            Table relationTable = connection.getTable(TableName.valueOf(TABLE_RELATION));
            //b.2 取出目标数据
            Get get = new Get(Bytes.toBytes(uid));
            get.addFamily(Bytes.toBytes("fans"));
            Result result = relationTable.get(get);
            List<byte[]> fans = new ArrayList<byte[]>();
            //遍历取出当前发布微博的用户的所有粉丝数据
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                fans.add(CellUtil.cloneQualifier(cell));
            }

            //如果该用户没有粉丝，直接return
            if (fans.size() < 0) return;

            //开始操作收件箱表
            Table inboxTable = connection.getTable(TableName.valueOf(TABLE_INBOX));
            //每一个粉丝都要向收件箱中添加该微博的内容，所以每一个粉丝都是一个put对象
            List<Put> puts = new ArrayList<>();
            for (byte[] fan : fans) {
                Put putFans = new Put(fan);
                putFans.addColumn(Bytes.toBytes("info"), Bytes.toBytes(uid), time, Bytes.toBytes(rowKey));
                puts.add(put);
            }
            inboxTable.put(puts);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
