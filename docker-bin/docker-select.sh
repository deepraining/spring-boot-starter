#!/bin/bash

# Usage: sh docker-select.sh [index]
# [index]: 选择运行最近的第几个镜像，默认是1
# 可以用于回滚应用

# Project name
project_name='pro-front'
# Group name
group_name='deepraining'
# App name
app_name='sbs-front-prod'
# Server env (spring.profiles.active)
server_env='prod'
# Docker container port
container_port=18001
# Docker host port
host_port=18001
# Java options
java_options='-Xms256m -Xmx512m'

index=0
if [ -z $1 ]; then
  index=1
elif [ "`echo $1|sed 's/[^0-9]//g'`" != $1 ]; then
  echo '[index] need a number'
  exit 1
else
  index=$1
fi

main(){
  if [ $index -lt 1 ]; then
    echo "[index] need a positive number"
    return 1
  fi
  images_count=$(docker images | grep "$group_name/$app_name" | wc -l | sed 's/ //g')
  if [ $images_count -eq 0 ]; then
    echo 'No images found'
    return 1;
  fi
  if [ $index -gt $images_count ]; then
    echo "[index] should be 1-$images_count"
    return 1
  fi

  app_version=$(docker images | grep "$group_name/$app_name" | head -${index} | tail -1 | awk '{print $2}')

  # 更新正在运行的镜像
  running_app=$(docker ps -a  --format "{{.Names}}" | grep "$app_name")
  if [ ! -z "${running_app}" ]; then
    echo "sh: docker stop $app_name"
    docker stop $app_name
    echo "sh: docker rm $app_name"
    docker rm $app_name
  fi

  docker run --restart=always -p $host_port:$container_port --name $app_name \
  -e SERVER_ENV=$server_env \
  -e JAVA_OPTS="$java_options" \
  -v /data/app/$app_name/logs:/var/logs \
  -v /data/app/$app_name/html:/var/html \
  -v /root/.$project_name:/root/.$project_name \
  -d $group_name/$app_name:$app_version

  running_status=$(docker ps -a --format "{{.Names}}\\t{{.Status}}" | grep "$app_name" | sed "s/$app_name//g" | sed 's/\t//g')
  running_version=$(docker ps -a --format "{{.Names}}\\t{{.Image}}" | grep "$app_name" | awk '{print $2}')
  if [ -z "$running_status" ]; then
    echo "$app_name failed to run in docker."
  else
    echo "$group_name/$app_name:$app_version is running now"
    echo "Running Version: $running_version"
    echo "Running Status: $running_status"
  fi
}

main
