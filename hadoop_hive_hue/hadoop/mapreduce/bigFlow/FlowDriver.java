package mapreduce.bigFlow;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//        1.配置信息
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(FlowDriver.class);
//        指定分区类
        job.setPartitionerClass(FlowPartitioner.class);
//        设置reduce的数量  reduce的数量就是最终文件的数量
//        设置的分区数应该注意，可以大于等于分区数，但是不能小于分区数
        job.setNumReduceTasks(2);

        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(flowQuantity.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(flowQuantity.class);

        FileInputFormat.setInputPaths(job, new Path("E:\\abc\\input\\phone_data.txt"));
        FileOutputFormat.setOutputPath(job, new Path("E:\\abc\\output05"));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
