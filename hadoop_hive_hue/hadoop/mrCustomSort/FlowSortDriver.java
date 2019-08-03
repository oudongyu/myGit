package mrCustomSort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowSortDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(FlowSortDriver.class);

        job.setMapperClass(FlowSortMap.class);
        job.setReducerClass(FlowSortReduce.class);

        job.setMapOutputKeyClass(flowQuantity.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(flowQuantity.class);

        FileInputFormat.setInputPaths(job,new Path("E:\\abc\\output03\\part-r-*"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\abc\\output07"));

        boolean b = job.waitForCompletion(true);
        if (b){
            System.out.println("执行成功！！");
        }else {
            System.out.println("执行失败！！");
        }
    }
}
