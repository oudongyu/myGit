package hdfs_test01;

import com.amazonaws.services.storagegateway.model.DisableGatewayRequest;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class hdfsClient01 {
    @Test
    public void initHDFS() throws IOException {
        //        1 创建配置信息对象作用：设置一些配置信息
//        配置信息可以在后台配置，也可以在代码配置。
//        不经常改动的配置在后台配置(比如说存放数据的位置)。经常改动的在代码配置(副本数)。
        Configuration configuration = new Configuration();
//        2 获取文件系统
        FileSystem fileSystem = FileSystem.get(configuration);
//        3 打印文件系统
        System.out.println(fileSystem.toString());
    }

    @Test
    public void copyFromLocal() throws URISyntaxException, IOException, InterruptedException {
//        1 获取文件系统
        Configuration configuration = new Configuration();
        //        优先级   通过副本数来测试
//        代码优先级>配置文件优先级>默认优先级
        configuration.set("dfs.replication", "2");
//        2 上传文件
//        想上传文件到hdfs上，条件是什么？ 首先需要知道hdfs的ip以及端口号。
//        需要知道上传的文件，以及上传到哪个地方
//        三个参数： 1.URI 就是hdfs的ip和端口 2.configuration配置文件 3.user 什么用户
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://houda01:9000"), configuration, "root");
        fileSystem.copyFromLocalFile(new Path("E:\\a.txt"), new Path("/"));
        System.out.println("上传文件成功！！");
//        3 关闭资源
        fileSystem.close();

    }

    @Test
    public void copyToLocal() throws URISyntaxException, IOException, InterruptedException {
//        1 获取文件系统
        Configuration configuration = new Configuration();

//        2 下载文件
//        三个参数： 1.URI 就是hdfs的ip和端口 2.configuration配置文件 3.user 什么用户
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://houda01:9000"), configuration, "root");
//        两个参数：1.hdfs上要下载的文件。  2.本地路径。下载到哪个地方
        fileSystem.copyToLocalFile(new Path("/du.txt"), new Path("e:\\"));
        System.out.println("下载文件成功！！");
//        3 关闭资源
        fileSystem.close();
    }

    @Test
//    创建文件夹，删除文件夹， 更改文件或文件夹名字
    public void hdfsMkdirs() throws URISyntaxException, IOException, InterruptedException {
//        1 获取文件系统
        Configuration configuration = new Configuration();

//        2 下载文件
//        三个参数： 1.URI 就是hdfs的ip和端口 2.configuration配置文件 3.user 什么用户
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://houda01:9000"), configuration, "root");
//        两个参数：1.hdfs上要下载的文件。  2.本地路径。下载到哪个地方
//        boolean b = fileSystem.mkdirs(new Path("/bbb/abc"));//d都可以

//        删除文件夹
//        delete传入两个参数：1.删除的文件路径 2. 是否递归删除
//        boolean b = fileSystem.delete(new Path("/a"), true);


//        更改名字 两个参数都是path类型，1.被修改的文件路径 2.修改成什么名字
        boolean b = fileSystem.rename(new Path("/ABC.txt"), new Path("/abc.txt"));
        System.out.println(b + "！！");
//        3 关闭资源
        fileSystem.close();
    }

    //    HDFS文件详情查看    查看文件名称、权限、长度、块信息
    @Test
    public void fileSelect() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://houda01:9000"), configuration, "root");
//         列出文件,返回迭代器
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path("/"), false);
//        通过while循环，用hasNext判断是否有数据，有的话输出，没有则跳出循环
        while (iterator.hasNext()) {
//            通过status就可以获取到文件的名称，权限等等
            LocatedFileStatus status = iterator.next();
//            获取文件的名字
            String name = status.getPath().getName();
//            获取文件的权限
            FsPermission permission = status.getPermission();
//            获取文件的长度
            long len = status.getLen();
//            获取副本数量
            short replication = status.getReplication();
            System.out.println("name:" + name + "\npermission:" + permission + "\nlen:" + len + "\nreplication:" + replication);
//            获取的块信息是谁的？   就是每个文件的所有块信息。
//            显示每个文件的block信息列表
            BlockLocation[] blockLocations = status.getBlockLocations();
//            常规for循环。也可以用增强for循环
            for (BlockLocation blockLocation : blockLocations) {
//                getHosts获取block块都在哪个host上
                String[] hosts = blockLocation.getHosts();
//                getTopologyPaths 获取block块都在哪个机架节点上
                String[] topologyPaths = blockLocation.getTopologyPaths();
//                还有很多方法。
                for (String host : hosts) {
                    System.out.print(host+"\t");
                }
                System.out.println();
                for (String topologyPath : topologyPaths) {
                    System.out.print(topologyPath+"\t");
                }
                System.out.println();
            }
        }
    }
    static ArrayList<String> arrayList = new ArrayList<>();
    public static void diGui(String path){

        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()){
                diGui(file1.toString());
            }else {
                arrayList.add(file1.toString());
                System.out.println(file1.toString());
            }
        }
    }

//    HDFS文件和文件夹判断
    @Test
    public void judgeFileOrDir() throws Exception{
        Configuration configuration = new Configuration();
        FileSystem root = FileSystem.get(new URI("hdfs://houda01:9000"), configuration, "root");
//

        File file = new File("E:\\sortware\\hadoop-2.7.2");
        File[] files = file.listFiles();
        for (File file1 : files) {
            System.out.println(file1);
        }
//        FileStatus[] fileStatuses = root.listStatus(new Path("/hadoop-2.7.2"));
//        for (FileStatus fileStatus : fileStatuses) {
////            isDirectory() 判断文件夹   isFile()判断文件
//            if (fileStatus.isFile()){
//                System.out.println(fileStatus.getPath()+"是文件！！");
//            }else {
//                System.out.println(fileStatus.getPath().getName()+"是文件夹！！");
//            }
//        }
    }

    public static void main(String[] args) {
        diGui("E:\\sortware\\hadoop-2.7.2");
    }

}
