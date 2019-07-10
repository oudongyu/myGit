package hdfs_test01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class hdfs_io {
    //    HDFS文件上传
    @Test
    public void io2HDFS() throws Exception {
        Configuration configuration = new Configuration();
//        1 获取文件系统
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.8.101:9000"), configuration, "root");
//        想通过io流方式上传 du02.txt文件流程：
//        1.指定文件路径 2.将du02.txt中的内容读取到内存 3.将内存中的数据写入到hdfs上。
//        2 创建输入流
        FileInputStream fis = new FileInputStream(new File("E:\\tmp\\Fourteen_class.txt"));
//        3 获取输出流
        FSDataOutputStream fos = fileSystem.create(new Path("/tmps/"));
//        new FileOutputStream();
//        4 流对拷 IOUtils工具类，可以将 数据拷入指定地方。 IOUtils是hadoop的工具类
//        三个参数：1.输入流 2.输出流 3.配置信息
        IOUtils.copyBytes(fis, fos, configuration);
//        关闭流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fileSystem.close();
    }

    @Test
    public void HDFS2Local() throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
//        1 获取文件系统
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.8.101:9000"), configuration, "root");
//        2 获取输入流
//        new FileInputStream();
        FSDataInputStream fis = fileSystem.open(new Path("/hadoop-2.7.2.tar.gz"));
//        3 创建输出流
        FileOutputStream fos = new FileOutputStream(new File("E:\\tmp\\hadoop-2.7.2.tar.gz.block01"));
//        4 流的对拷
        byte[] bytes = new byte[1024];
        for (int i = 0; i < 1024 * 128; i++) {
            fis.read(bytes);
            fos.write(bytes);
        }
        System.out.println("写入本地文件成功！");
//        5 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fileSystem.close();
    }
    @Test
    public void HDFS2Local02() throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
//        1 获取文件系统
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.8.101:9000"), configuration, "root");
//        2 获取输入流
//        new FileInputStream();
        FSDataInputStream fis = fileSystem.open(new Path("/hadoop-2.7.2.tar.gz"));
//        3 创建输出流
        FileOutputStream fos = new FileOutputStream(new File("E:\\tmp\\hadoop-2.7.2.tar.gz.block02"));
//        定位数据！
        fis.seek(128*1024*1024);// b 单位
        //        4 流的对拷
        IOUtils.copyBytes(fis,fos,configuration);
        System.out.println("写入本地文件成功！");
//        5 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fileSystem.close();
    }
}
