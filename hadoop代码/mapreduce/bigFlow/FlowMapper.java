package mapreduce.bigFlow;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text, Text, flowQuantity> {

    Text t = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        分割每行内容，获取手机号，上行，下行流量
        String s = value.toString();
        String[] split = s.split("\t");
        String phoneNumber = split[1];
        String upFlow = split[split.length-3];
        String downFlow = split[split.length-2];
//        Long.valueOf(upFlow);
//        Double.v
        flowQuantity flowQuantity = new flowQuantity(Long.valueOf(upFlow), Long.valueOf(downFlow));
        t.set(phoneNumber);
        context.write(t,flowQuantity);

    }
}
