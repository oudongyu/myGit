package mapreduce.bigFlow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class FlowReducer extends Reducer<Text, flowQuantity, Text, flowQuantity> {

    @Override
    protected void reduce(Text key, Iterable<flowQuantity> values, Context context) throws IOException, InterruptedException {
//        相同key的value。
//        上行，下行，总流量
        long sumUpFlow = 0;
        long sumDownFlow = 0;
        for (flowQuantity value : values) {
            sumUpFlow += value.getUpFlow();
            sumDownFlow += value.getDownFlow();
        }

        flowQuantity flowQuantity = new flowQuantity(sumUpFlow, sumDownFlow);
//        sumUpFlow+"\t"+sumDownFlow+"\t"+flowQuantity.getSumFlow()
        context.write(key, flowQuantity);
    }
}
