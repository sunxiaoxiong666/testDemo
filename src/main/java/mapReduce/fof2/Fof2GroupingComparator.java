package mapReduce.fof2;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Fof2GroupingComparator extends WritableComparator {
    public Fof2GroupingComparator() {
        super(Fof.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Fof a1 = (Fof) a;
        Fof b1 = (Fof) b;
        //按照名称字典排序
        return a1.getAname().compareTo(b1.getAname());
    }
}
