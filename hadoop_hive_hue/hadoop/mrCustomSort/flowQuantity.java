package mrCustomSort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class flowQuantity implements WritableComparable<flowQuantity> {
//    实现自定义序列化和排序

    private long upFlow;
    private long downFlow;
    private long sumFlow;

    //    反序列化时需要
    public flowQuantity() {
        super();
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    public void readFields(DataInput in) throws IOException {
        upFlow = in.readLong();
        downFlow = in.readLong();
        sumFlow = in.readLong();
    }


    public int compareTo(flowQuantity o) {
//        获取汇总流量
//        然后比较并排序
//        return this.sumFlow > o.sumFlow ? -1 : 1;

//        二次排序：
//        需求：下行流量 降序排列，当下行流量相同时，上行流量升序排列
        int num = 0;
        if (this.downFlow > o.downFlow) {
            num = -1;
        } else if (this.downFlow < o.downFlow) {
            num = 1;
        } else {
            if (this.upFlow > o.upFlow) {
                num = 1;
            } else if (this.upFlow < o.upFlow) {
                num = -1;
            }
        }
        return num;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }
}
