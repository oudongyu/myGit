package hdfs_test01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class hdfs_test {
    //    1.1.通过IO流的方式将hadoop-2.7.2中的所有文件（需要自己判断，（需要用到递归）
//）上传到hdfs的/a/b文件夹中。（此多级文件夹通过api代码实现）
//    递归的特点：自己调用自己
    ArrayList<String> arrayList = new ArrayList<>();

    public void judgeIsFile(String path) {
//        指定路径
        File file = new File(path);
//        列出此路径所有内容
        File[] files = file.listFiles();
        for (File f1 : files) {
            if (f1.isDirectory()) {
                judgeIsFile(f1.toString());
            } else {
                System.out.println(f1.toString() + "是文件！");
                arrayList.add(f1.toString());
            }
        }
    }

    //    连接hdfs
    Configuration configuration = new Configuration();

    public FileSystem connection2hadoop() {
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(new URI("hdfs://houda01:9000"), configuration, "root");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return fileSystem;
    }

    //    遍历list，读取文件内容，然后上传到hdfs上
    public void read2hdfs(ArrayList<String> arrayList) {
        FileSystem fileSystem = connection2hadoop();
        try {
            boolean mkdirs = fileSystem.mkdirs(new Path("/a/b"));
            System.out.println(mkdirs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream fis = null;
        FSDataOutputStream fos = null;
        for (String s : arrayList) {
            try {
                File file = new File(s);
                String name = file.getName();
                fis = new FileInputStream(file);
                fos = fileSystem.create(new Path("/a/b/" + name));
                IOUtils.copyBytes(fis, fos, configuration);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        try {
            fileSystem.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    2.读取/a/b目录，输出每个文件的名字,权限,副本数,大小(单位是KB),以及block块的位置(host)


    public static void main(String[] args) {
        hdfs_test hdfs_test = new hdfs_test();
        hdfs_test.judgeIsFile("E:\\sortware\\hadoop-2.7.2");
        hdfs_test.read2hdfs(hdfs_test.arrayList);

    }
}
