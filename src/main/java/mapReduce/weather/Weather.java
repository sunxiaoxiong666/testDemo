package mapReduce.weather;

/*
 * @author: sunxiaoxiong
 */

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Weather implements WritableComparable<Weather> {

    private Integer year;
    private Integer month;
    private Integer date;
    private Integer temperature;//气温

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    @Override
    public int compareTo(Weather o) {
        //比较年月日
        int year = o.getYear();
        int month = o.getMonth();
        int date = o.getDate();
        int temperature = o.getTemperature();

        int result = this.year.compareTo(year);
        if (result == 0) {
            //compareTo表示this的值大于that的值，则1
            //compareTo表示this的值小于that的值，则-1
            //相等则为0
            result = this.month.compareTo(month);
            if (result == 0) {
                result = this.date.compareTo(date);
            }
        }
        return result;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        //序列化，写出去
        dataOutput.writeInt(getYear());
        dataOutput.writeInt(getMonth());
        dataOutput.writeInt(getDate());
        dataOutput.writeInt(getTemperature());

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        //读数据，反序列化
        setYear(dataInput.readInt());
        setMonth(dataInput.readInt());
        setDate(dataInput.readInt());
        setTemperature(dataInput.readInt());

    }
}
