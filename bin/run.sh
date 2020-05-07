#!/bin/bash

# Everytime to update project version, you can copy lastest jar under libs directory
# to current directory, and name it starter.jar(any name is ok as you like).
FILE_NAME=starter
APP_NAME="${FILE_NAME}.jar"
# Server env, modify it as you like
SERVER_ENV=prod

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
    nohup java -server -Xms256m -Xmx512m -jar $APP_NAME --spring.profiles.active=$SERVER_ENV > /dev/null 2>&1 &
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

version(){
  fileSize=`du -k $APP_NAME|awk '{print $1}'`
  if [ -z "$fileSize" ]; then
    return 1;
  fi

  jarFiles=`du -k libs/${FILE_NAME}-*.jar`
  if [ -z "$jarFiles" ]; then
    return 1;
  fi

  lastItem=""
  findItem=""
  for item in $jarFiles
  do
    if [ "$lastItem" = "$fileSize" ]; then
      findItem=$item
    fi
    lastItem=$item
  done

  if [ -z "$findItem" ]; then
    echo "Version not detected."
    return 1
  fi

  tempStr=${findItem##*-}
  v=${tempStr%%.jar}
  echo $v
}

restart(){
  stop
  start
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
  "version")
    version
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
