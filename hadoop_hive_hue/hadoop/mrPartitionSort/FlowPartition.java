package mrPartitionSort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlowPartition extends Partitioner<flowQuantity,Text> {
    public int getPartition(flowQuantity flowQuantity, Text text, int numPartitions) {
        String s = text.toString().substring(0, 3);
        int partition=6;
        if (s.equals("134")){
            partition=0;
        }else if (s.equals("135")){
            partition=1;
        }else if (s.equals("136")){
            partition=2;
        }else if (s.equals("137")){
            partition=3;
        }else if (s.equals("138")){
            partition=4;
        }else if (s.equals("139")){
            partition=5;
        }
        return partition;
    }
}
