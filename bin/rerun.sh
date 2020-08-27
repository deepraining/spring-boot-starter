#!/bin/bash

# Usage: sh rerun.sh [index]
# [index]: 运行最近的第几个jar文件，默认是1
FILE_NAME=starter
APP_NAME="${FILE_NAME}.jar"
# 版本文件
VERSION_FILE='version.txt'

index=0
if [ -z $1 ]; then
  index=1
elif [ "`echo $1|sed 's/[^0-9]//g'`" != $1 ]; then
  echo '[index] need number'
else
  index=$1
fi

main(){
  if [ $index -lt 1 ]; then
    return 1
  fi
  jarFilesCount=`ls libs/${FILE_NAME}-*.jar|wc -l|sed 's/ //g'`
  if [ $jarFilesCount -eq 0 ]; then
    return 1;
  fi
  if [ $index -gt $jarFilesCount ]; then
    echo "[index] should be 1-$jarFilesCount"
    return 1
  fi

  jarFile=`ls libs/${FILE_NAME}-*.jar|tail -${index}|head -1`

  echo "sh: cp ${jarFile} ${APP_NAME}"
  cp $jarFile $APP_NAME

  # save current version
  tempStr=${jarFile##*-}
  version=${tempStr%%.jar}
  echo $version > $VERSION_FILE

  echo 'sh: sh run.sh restart'
  sh run.sh restart
}

main
