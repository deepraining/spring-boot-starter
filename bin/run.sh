#!/bin/bash

# lastest jar under libs directory
APP_NAME=`ls libs/*.jar | tail -n 1`
# server env, modify it as you like
SERVER_ENV=prod

usage() {
  echo "Usage: sh $0 [start|stop|restart|status]"
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
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
