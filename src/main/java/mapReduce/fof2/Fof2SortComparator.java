package mapReduce.fof2;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Fof2SortComparator extends WritableComparator {
    public Fof2SortComparator() {
        super(Fof.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Fof a1 = (Fof) a;
        Fof b1 = (Fof) b;
        //按照名称字典排序
        int result = a1.getAname().compareTo(b1.getAname());

        if (result == 0) {
            //按照共同好友数倒叙排列
            result = -a1.getNum().compareTo(b1.getNum());
        }
        return result;
    }
}
