#!/bin/bash

# Usage: sh docker-run.sh [start|stop|restart|status|version]
# 运行项目的脚本

# App name
app_name='sbs-front-prod'

usage() {
  echo "Usage: sh $0 [start|stop|restart|status|version]"
  exit 1
}

start(){
  docker start $app_name
  echo "$app_name started"
  status
}

stop(){
  docker stop $app_name
  echo "$app_name stopped"
  status
}

status(){
  status=$(docker ps -a --format "{{.Names}}\\t{{.Status}}" | grep "$app_name" | sed "s/$app_name//g" | sed 's/\t//g')
  if [ -z "$status" ]; then
    echo "$app_name do not run in docker."
  else
    echo "$status"
  fi
}

restart(){
  docker restart $app_name
  echo "$app_name restarted"
  status
}

version(){
  version=$(docker ps -a --format "{{.Names}}\\t{{.Image}}" | grep "$app_name" | awk '{print $2}')
  if [ -z "$version" ]; then
    echo "$app_name do not run in docker."
  else
    echo "$version"
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
