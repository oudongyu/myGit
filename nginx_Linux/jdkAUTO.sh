#!/bin/bash
# echo $#
if [ $# -lt 2 ];then
echo "请输入你的jdk安装包路径和安装路径"
exit 1
fi

path=$1
path02=$2
tar	-zxvf $path -C $path02
if [ $? == 1 ];then
echo "解压失败"
else
echo -e "解压成功\n即将配置环境变量！"
fi

for i in `ls -t ${path02}`
do
dirSize=`du -s ${path02}/$i|awk '{print $1}'`
echo $dirSize
if [ ${dirSize} -gt 104857 ];then
jdkName=$i
break
fi
done

echo "export JAVA_HOME=${path02}/${jdkName}" >> /etc/profile
echo 'export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH' >> /etc/profile

echo "开始刷新环境变量"
source /etc/profile
source ~/.bashrc

java -version
if [ $? == 1 ];then
echo "安装失败!"
else
echo "安装成功!"
fi

