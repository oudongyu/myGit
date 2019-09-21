#!/bin/bash
read -p 请输入第一台机器主机名: a
read -p 请输入第二台机器主机名: b
read -p 请输入第三台机器主机名: c

hadoopPath="${HADOOP_HOME}/etc/hadoop"
echo $hadoopPath
jdkPath=${JAVA_HOME}
cd $hadoopPath
sed -i '/export JAVA_HOME=${JAVA_HOME}/d' hadoop-env.sh
echo "export JAVA_HOME=${jdkPath}" >> hadoop-env.sh

sed -i -e 's/<\/configuration>//g' -e '/^$/d' core-site.xml 
echo "<!--配置HDFS文件系统的命名空间-->
<property>
<name>fs.defaultFS</name>
<value>hdfs://${a}:9000</value>
</property>
<!--HDFS读取文件的缓冲大小-->
<property>
<name>io.file.buffer.size</name>
<value>4096</value>
</property>
</configuration>" >> core-site.xml

sed -i -e 's/<\/configuration>//g' -e '/^$/d' hdfs-site.xml
echo "<!--配置hdfs文件系统的副本数-->
<property>
<name>dfs.replication</name>
<value>3</value>
</property>
<!--指定hdfs文件系统的元数据存放目录-->
<property>
<name>dfs.namenode.name.dir</name>
<value>file:///opt/hadoopdata/dfs/name</value>
</property>
<!--指定hdfs文件系统的数据块存放目录-->
<property>
<name>dfs.datanode.data.dir</name>
<value>file:///opt/hadoopdata/dfs/data</value>
</property>
<!--配置HDFS的web管理地址-->
<property>
<name>dfs.http.address</name>
<value>${a}:50070</value>
</property>
<!--配置secondaryNamenode的web管理地址-->
<property>
<name>dfs.secondary.http.address</name>
<value>${b}:50090</value>
</property>
<!--配置是否打开web管理-->
<property>
<name>dfs.webhdfs.enabled</name>
<value>true</value>
</property>
<!--指定hdfs文件系统权限是否开启-->
<property>
<name>dfs.permissions</name>
<value>false</value>
</property>
</configuration>" >> hdfs-site.xml

cp mapred-site.xml.template mapred-site.xml
sed -i -e 's/<\/configuration>//g' -e '/^$/d' mapred-site.xml
echo "<!--指定mapreduce运行的框架名-->
<property>
<name>mapreduce.framework.name</name>
<value>yarn</value>
<final>true</final>
</property>
<!--配置mapreduce的历史记录组件的内部通信地址-->
<property>
<name>mapreduce.jobhistory.address</name>
<value>${a}:10020</value>
</property>
<!--配置mapreduce的历史记录服务的web管理地址-->
<property>
<name>mapreduce.jobhistory.webapp.address</name>
<value>${a}:19888</value>
</property>
<property>
<name>mapreduce.job.ubertask.enable</name>
<value>true</value>
</property>
<property>
<name>mapreduce.job.ubertask.maxmaps</name>
<value>9</value>
</property>
<property>
<name>mapreduce.job.ubertask.maxreduces</name>
<value>1</value>
</property>
</configuration>" >> mapred-site.xml

sed -i -e 's/<\/configuration>//g' -e '/^$/d' yarn-site.xml
echo "<!-- Site specific YARN configuration properties -->
<!--指定resourcemanager所启动服务的主机名/ip-->
<property>
<name>yarn.resourcemanager.hostname</name>
<value>${a}</value>
</property>
<!--指定mapreduce的shuffle处理数据方式-->
<property>
<name>yarn.nodemanager.aux-services</name>
<value>mapreduce_shuffle</value>
</property>
<!--配置resourcemanager内部通讯地址-->
<property>
<name>yarn.resourcemanager.address</name>
<value>${a}:8032</value>
</property>
<!--配置resourcemanager的scheduler组件的内部通信地址-->
<property>
<name>yarn.resourcemanager.scheduler.address</name>
<value>${a}:8030</value>
</property>
<!--配置resource-tracker组件的内部通信地址-->
<property>
<name>yarn.resourcemanager.resource-tracker.address</name>
<value>${a}:8031</value>
</property>
<!--配置resourcemanager的admin的内部通信地址-->
<property>
<name>yarn.resourcemanager.admin.address</name>
<value>${a}:8033</value>
</property>
<!--配置yarn的web管理地址-->
<property>
<name>yarn.resourcemanager.webapp.address</name>
<value>${a}:8088</value>
</property>

<!--yarn的聚合日志是否开启-->
  <property>
    <name>yarn.log-aggregation-enable</name>
    <value>true</value>
  </property>
<!--聚合日志报错hdfs上的时间-->
  <property>
    <name>yarn.log-aggregation.retain-seconds</name>
    <value>86400</value>
  </property>
  <!--聚合日志的检查时间段-->
  <property>
    <name>yarn.log-aggregation.retain-check-interval-seconds</name>
    <value>3600</value>
  </property>
<!---->
  <property>
    <name>yarn.nodemanager.log.retain-seconds</name>
    <value>10800</value>
  </property>
<!--当应用程序运行结束后，日志被转移到的HDFS目录（启用日志聚集功能时有效）-->
  <property>
    <name>yarn.nodemanager.remote-app-log-dir</name>
    <value>/hadoopdata/logs</value>
  </property>
</configuration>" >> yarn-site.xml

>slaves
echo -e "${a}\n${b}\n${c}" >> slaves

echo "hadoop基本配置结束！"







