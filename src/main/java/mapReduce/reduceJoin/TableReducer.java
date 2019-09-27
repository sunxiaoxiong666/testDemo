package mapReduce.reduceJoin;

/*
 * @author: sunxiaoxiong
 */

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {
        /*
        传过来的数据格式为：
         * 01 1001  1
         * 01 1002  2
         * 01 小米
         * */

        //1.创建存放订单的集合
        ArrayList<TableBean> tableBeans = new ArrayList<>();
        //2.准备产品表的bean对象
        TableBean pdBean = new TableBean();

        for (TableBean bean : values) {
            if ("0".equals(bean.getFlag())) {//订单表
                //拷贝传递过来的每条订单数据到集合中
                TableBean oderBean = new TableBean();
                try {
                    BeanUtils.copyProperties(oderBean, bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tableBeans.add(oderBean);
            } else {//产品表
                try {
                    //拷贝传递过来的产品表到缓存中，传过来的values中只有一条产品表的记录
                    BeanUtils.copyProperties(pdBean, bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //3. 表的拼接
        for (TableBean bean : tableBeans) {
            bean.setPname(pdBean.getPname());

            //4.数据写出去
            context.write(bean, NullWritable.get());
        }
    }
}
