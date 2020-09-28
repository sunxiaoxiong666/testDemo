package hbase.weibo_project;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    private  Configuration conf = HBaseConfiguration.create();
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
                    .addConfiguration("creator", "xiong")
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
            //设置块缓存大小2M
            infoColumnFamilyDescriptor.setBlocksize(2 * 1024 * 1024);
            //设置压缩方式
            // infoColumnFamilyDescriptor.setCompressionType(Compression.Algorithm.SNAPPY);
            //设置版本确界，因为内容表中一个单元只放一条微博内容就行。
            infoColumnFamilyDescriptor.setMaxVersions(1);
            infoColumnFamilyDescriptor.setMinVersions(1);
            //将列描述器放在表描述器中。
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
            //b.2 取出粉丝的id放到list集合中
            Get get = new Get(Bytes.toBytes(uid));
            get.addFamily(Bytes.toBytes("fans"));
            Result result = relationTable.get(get);
            List<byte[]> fans = new ArrayList<byte[]>();
            //遍历取出当前发布微博的用户的所有粉丝数据
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                fans.add(CellUtil.cloneValue(cell));
            }

            //如果该用户没有粉丝，直接return
            if (fans.size() < 0) {
                return;
            }

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

    /*
     * 6.添加关注用户
     * a.在微博关系用户表中，对当前操作用户添加新的关注用户
     * b.在微博关系用户表中，对被关注的用户添加新的粉丝用户(当前用户)
     * c.当前操作用户的微博收件箱中，添加关注用户发的微博的rowKey
     * */

    public void addAttends(String uid, String... attends) {
        //参数过滤
        if (attends == null || attends.length <= 0 || uid == null || uid.length() <= 0) {
            return;
        }
        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(conf);
            //用户关系表操作对象（连接到用户关系表）
            Table relationTable = connection.getTable(TableName.valueOf(TABLE_RELATION));
            List<Put> puts = new ArrayList<>();
            //a.在关系用户表中，添加新的关注好友
            Put attendPut = new Put(Bytes.toBytes(uid));
            for (String attend : attends) {
                //为当前用户添加关注的人
                attendPut.addColumn(Bytes.toBytes("attends"), Bytes.toBytes(attend), Bytes.toBytes(attend));
                //b.为被关注的人添加粉丝
                Put fansPut = new Put(Bytes.toBytes(attend));
                fansPut.addColumn(Bytes.toBytes("fans"), Bytes.toBytes(uid), Bytes.toBytes(uid));
                //将所有关注的人一个一个添加到puts集合中
                puts.add(fansPut);
            }
            puts.add(attendPut);
            relationTable.put(puts);

            //c.1 微博收件箱添加关注用户发布的微博内容的rowkey
            Table contentTable = connection.getTable(TableName.valueOf(TABLE_CONTENT));
            Scan scan = new Scan();
            //用于存放取出来关注人发布微博的rowkey
            List<byte[]> rowKeys = new ArrayList<>();
            for (String attend : attends) {
                //过滤扫描rowkey，即：前置位匹配被关注的人的uid
                RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(attend + "_"));
                //为扫描对象指定过滤规则
                scan.setFilter(filter);
                //通过扫描对象得到scanner
                ResultScanner results = contentTable.getScanner(scan);
                //迭代器遍历扫描出来的结果集
                Iterator<Result> iterator = results.iterator();
                while (iterator.hasNext()) {
                    //取出每一个符合扫描结果的那一行数据
                    Result r = iterator.next();
                    Cell[] cells = r.rawCells();
                    for (Cell cell : cells) {
                        //将得到的rowkey放置于集合容器中
                        rowKeys.add(CellUtil.cloneRow(cell));
                    }
                }

            }
            //c.2 将取出的微博rowkey放置于当前操作用户的收件箱中
            if (rowKeys.size() <= 0) {
                return;
            }
            //得到微博收件箱表的操作对象
            Table inboxTable = connection.getTable(TableName.valueOf(TABLE_INBOX));
            //用于存放多个关注用户发布的多条微博的rowkey信息
            List<Put> inboxPut = new ArrayList<>();
            for (byte[] rk : rowKeys) {
                Put put = new Put(Bytes.toBytes(uid));
                //uid_timestamp
                String rowKey = Bytes.toString(rk);
                //截取uid
                String attendUid = rowKey.substring(0, rowKey.indexOf("_"));
                long timestamp = Long.parseLong(rowKey.substring(rowKey.indexOf("_") + 1));
                //将微博rowkey添加到指定单元格中
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(attendUid), timestamp, rk);
                inboxPut.add(put);
            }
            inboxTable.put(inboxPut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 7.移除(取消关注)用户
     * a.在微博用户关系表中，对当前主动操作的用户删除取关的好友
     * b.在微博用户关系表中，对被取消关注的用户，删除对应的粉丝(当前操作人)
     * c.在收件箱中，删除取关人微博的rowkey
     * */
    public void removeAttends(String uid, String... attends) {
        //过滤数据
        if (uid == null || uid.length() <= 0 || attends == null || attends.length <= 0) {
            return;
        }

        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(conf);
            //a. 在微博用户关系表中，删除已关注的好友
            Table relationTable = connection.getTable(TableName.valueOf(TABLE_RELATION));
            //待删除的用户关系表中的所有数据
            List<Delete> deletes = new ArrayList<>();
            //当前取关操作者的uid对应的delete对象
            Delete attendDelete = new Delete(Bytes.toBytes(uid));
            //遍历取关，同时每次取关都要将被取关的人的粉丝-1
            for (String attend : attends) {
                attendDelete.addColumn(Bytes.toBytes("attends"), Bytes.toBytes(attend));
                //b. 在微博用户关系表中，对被取消关注的人删除粉丝（当前操作人）
                Delete fansDelete = new Delete(Bytes.toBytes(attend));
                fansDelete.addColumn(Bytes.toBytes("fans"), Bytes.toBytes(uid));
                deletes.add(fansDelete);
            }
            deletes.add(attendDelete);
            relationTable.delete(deletes);

            //c.在收件箱表中删除取关人微博的rowkey
            Table inboxTable = connection.getTable(TableName.valueOf(TABLE_INBOX));
            Delete inboxDelete = new Delete(Bytes.toBytes(uid));
            for (String attend : attends) {
                inboxDelete.addColumn(Bytes.toBytes("info"), Bytes.toBytes(attend));
            }
            inboxTable.delete(inboxDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 8.获取关注人的微博内容
     * a.从微博收件箱中获取所有关注人发布微博的rowkey
     * b.根据得到的rowkey去微博内容表中得到数据
     * c.将得到的数据封装到message对象中
     * */
    public List<Message> getAttendsContents(String uid) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(conf);
            Table inboxTable = connection.getTable(TableName.valueOf(TABLE_INBOX));
            //a. 从收件箱中取得微博rowkey
            Get get = new Get(Bytes.toBytes(uid));
            //设置最大版本号，每个cell中存放好多微博，只取最新的5个微博
            get.setMaxVersions(5);
            //准备一个存放rowkey的集合
            List<byte[]> rowkeys = new ArrayList<>();
            Result result = inboxTable.get(get);
            for (Cell cell : result.rawCells()) {
                rowkeys.add(CellUtil.cloneValue(cell));
            }

            //b. 根据rowkey取出对应微博的具体内容
            Table contentTable = connection.getTable(TableName.valueOf(TABLE_CONTENT));
            List<Get> gets = new ArrayList<>();
            //根据rowkey取出对应微博的具体内容
            for (byte[] rk : rowkeys) {
                Get g = new Get(rk);
                gets.add(g);
            }
            //得到所有微博内容的result对象
            Result[] results = contentTable.get(gets);
            //将每一条微博内容都封装为消息对象
            List<Message> messages = new ArrayList<>();
            for (Result result1 : results) {
                for (Cell cell : result1.rawCells()) {
                    Message message = new Message();
                    String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
                    String userid = rowKey.substring(0, rowKey.indexOf("_"));
                    String timestamp = rowKey.substring(rowKey.indexOf("_") + 1);
                    String content = Bytes.toString(CellUtil.cloneValue(cell));
                    message.setContent(content);
                    message.setTimestamp(timestamp);
                    message.setUid(userid);
                    messages.add(message);
                }
            }
            return messages;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //测试发布微博内容
    public void testPublishWeiBo(WeiBo wb) {
        wb.publishContent("0001", "测试发布微博内容");
        wb.publishContent("0001", "天气有点冷");
    }

    //添加关注
    public void testAddAttend(WeiBo wb) {
        wb.publishContent("0008", "准备下课");
        wb.publishContent("0009", "准备关机");
        wb.addAttends("0001", "0008", "0009");
    }

    //取消关注
    public void testRemoveAttend(WeiBo wb) {
        wb.removeAttends("0001", "0008");
    }

    //查看关注人的微博内容
    public void testShowMessage(WeiBo wb) {
        List<Message> messages = wb.getAttendsContents("0001");
        for (Message message : messages) {
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        WeiBo wb = new WeiBo();
        wb.initNameSpace();

        wb.testPublishWeiBo(wb);
        wb.testAddAttend(wb);
        wb.testRemoveAttend(wb);
        wb.testShowMessage(wb);
    }
}
