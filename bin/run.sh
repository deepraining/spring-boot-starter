#!/bin/bash

# Usage: sh run.sh [start|stop|restart|status|version]
# 运行项目的脚本

# Server env (spring.profiles.active)
SERVER_ENV=prod
# jar归档文件基础名称，配置在 build.gradle 中的 archivesBaseName
ARCHIVE_NAME=starter
# 实际运行的应用名称
APP_NAME="${ARCHIVE_NAME}.jar"
# 版本文件（会将正在运行的版本号放在这个文件中）
VERSION_FILE='version.txt'

usage() {
  echo "Usage: sh $0 [start|stop|restart|status|version]"
  exit 1
}

running(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
  if [ -z "${pid}" ]; then
    return 1
  else
    return 0
  fi
}

start(){
  running
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running, pid=${pid} ."
  else
    # 产品环境中，参数-Xms与-Xmx可以设置为相同的值，这样可以减轻伸缩堆大小带来的压力，提高性能
    # 根据应用的需要可以增加堆内存大小，比如 -Xms4g -Xmx4g
    nohup java -server -Xms256m -Xmx512m -jar $APP_NAME --spring.profiles.active=$SERVER_ENV > nohup.out 2>&1 &
    echo "${APP_NAME} starts succeeded, and view the logs to confirm that program has already been started."
  fi
}

stop(){
  running
  if [ $? -eq "0" ]; then
    kill -9 $pid
    echo "${pid} has already been killed, and program stopped running."
  else
    echo "${APP_NAME} is not running."
  fi
}

status(){
  running
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running, pid=${pid} ."
  else
    echo "${APP_NAME} is not running."
  fi
}

restart(){
  stop
  start
}

version(){
  if [ -f $VERSION_FILE ]; then
    cat $VERSION_FILE
  else
    echo "No version found"
  fi
}

case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  "version")
    version
    ;;
  *)
    usage
    ;;
esac
