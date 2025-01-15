#!/bin/bash

# Usage: sh select.sh [index]
# [index]: 选择运行最近的第几个jar文件，默认是1
# 可以用于回滚应用

set -e

# jar归档文件基础名称，配置在 build.gradle 中的 archivesBaseName
jar_name='sbs-front'
# 实际运行的应用名称
app_name="${jar_name}-prod.jar"
# 版本文件（会将正在运行的版本号放在这个文件中）
version_file='version.txt'

index=0
if [ -z $1 ]; then
  index=1
elif [ "`echo $1|sed 's/[^0-9]//g'`" != $1 ]; then
  echo '[index] need number'
  exit 1
else
  index=$1
fi

if [ $index -lt 1 ]; then
  echo 'No jar file selected to run'
  return 1
fi
jarFilesCount=`ls libs/${jar_name}-*.jar|wc -l|sed 's/ //g'`
if [ $jarFilesCount -eq 0 ]; then
  echo 'No jar files found under libs directory'
  return 1;
fi
if [ $index -gt $jarFilesCount ]; then
  echo "[index] should be 1-$jarFilesCount"
  return 1
fi

jarFile=`ls libs/${jar_name}-*.jar|tail -${index}|head -1`

echo "sh: cp ${jarFile} ${app_name}"
cp $jarFile $app_name

# save current version
tempStr=${jarFile##*-}
version=${tempStr%%.jar}
echo $version > $version_file

echo 'sh: sh bin/run.sh restart'
sh bin/run.sh restart
