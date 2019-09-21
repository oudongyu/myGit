#!/bin/bash
if [ $# -lt 2 ];then
echo "请输入你的hadoop安装包路径和安装路径"
exit 1
fi
sourcePath=$1
targetPath=$2
profilePath="/etc/profile"
tar -zxvf $sourcePath -C $targetPath
# echo "$a,${#a}"
echo "即将配置环境变量！"
hadoopName=`ls -l ${targetPath} |grep "2.7.2" | awk '{print $NF}'`

echo "export HADOOP_HOME=${targetPath}/${hadoopName}" >> $profilePath
echo 'export PATH=${HADOOP_HOME}/bin:${HADOOP_HOME}/sbin:$PATH' >> $profilePath

source $profilePath
source ~/.bashrc
hadoop version










