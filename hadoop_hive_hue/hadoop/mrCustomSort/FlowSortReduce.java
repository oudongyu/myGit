package mrCustomSort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowSortReduce extends Reducer<flowQuantity,Text,Text,flowQuantity> {
    @Override
    protected void reduce(flowQuantity key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//        由于flowQuantity是对象，有唯一的地址值，所以迭代器中只有一个数据。手机号
        context.write(values.iterator().next(),key);
    }
}
