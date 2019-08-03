package mrCustomSort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//由于需要对流量汇总做排序。而且shuffle中只会对map的key排序，所以应该将流量对象放到key中
public class FlowSortMap extends Mapper<LongWritable,Text,flowQuantity,Text> {
    flowQuantity flowQuantity = new flowQuantity();
    Text text = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        读取数据格式如下：
//        13480253104	180	180	360
//        13502468823	7335	110349	117684
//        13560436666	3597	25635	29232
//        1.分割每行数据。2.将上行，下行，汇总流量设置到流量对象中，将手机号设置到map的value中
        String[] strings = value.toString().split("\t");
        text.set(strings[0]);
        flowQuantity.setUpFlow(Long.valueOf(strings[1]));
        flowQuantity.setDownFlow(Long.valueOf(strings[2]));
        flowQuantity.setSumFlow(Long.valueOf(strings[3]));
        context.write(flowQuantity,text);

    }
}
