package mapreduce.bigFlow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlowPartitioner extends Partitioner<Text,flowQuantity> {
    public int getPartition(Text text, flowQuantity flowQuantity, int numPartitions) {
//        手机号 13826544101  13926435656
//        截取谁的方法？ string
//        String phoneNumber = text.toString();
//        String s = phoneNumber.substring(0, 3);//下标从0开始，包左不包右
//        int partitioner=4;
//        if (s.equals("138")){
//            partitioner=0;
//        }else if (s.equals("139")){
//            partitioner=1;
//        }else if (s.equals("135")){
//            partitioner=2;
//        }else if (s.equals("159")){
//            partitioner=3;
//        }
//        return partitioner;
//        我想根据汇总流量的奇偶分区
//        1.获取汇总流量   flowQuantity.getSumFlow()
//        2.判断奇偶        sum%2
        long sumFlow = flowQuantity.getSumFlow();
        return (int) (sumFlow % 2);

    }
}
