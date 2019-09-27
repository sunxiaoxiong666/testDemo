package mapReduce.fof2;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Fof implements WritableComparable<Fof> {

    private String aname;
    private String bname;
    //共同好友数
    private Integer num;

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public int compareTo(Fof o) {
        int result = aname.compareTo(o.getAname());
        if (result == 0) {
            result = bname.compareTo(o.getBname());
            if (result == 0) {
                result = num.compareTo(o.getNum());
            }
        }
        return result;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(aname);
        dataOutput.writeUTF(bname);
        dataOutput.writeInt(num);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        setAname(dataInput.readUTF());
        setBname(dataInput.readUTF());
        setNum(dataInput.readInt());
    }
}
