#!/bin/bash

# 构建并本地运行(以pro-front项目为例)

# Build directory
build_dir='project'
# Project name
project_name='pro-front'
# Group name
group_name='deepraining'
# App name
app_name='sbs-front-prod'
# Jar name
jar_name='sbs-front'

# 最多保存版本数
max_versions=3

cd "$build_dir"

# 构建Java项目
rm -rf $project_name/build/libs/*
rm -rf $project_name/build/distributions/*
chmod +x gradlew
./gradlew $project_name:build

app_version=$(ls $project_name/build/libs | sed "s/.*${jar_name}-//" | sed 's/.jar$//')

echo "$app_name version: $app_version"

# 构建docker镜像
docker build --build-arg JAR_FILE=$project_name/build/libs/$jar_name-$app_version.jar -t $group_name/$app_name:$app_version .

# 删除多余的镜像
# 当前存在的版本
existed_versions=$(docker images $group_name/$app_name | grep "$group_name/$app_name" | wc -l)

if [ $existed_versions -gt $max_versions ]; then
  # 删除多余的版本
  overflow_versions=$((existed_versions - max_versions))
  delete_versions=$(docker images $group_name/$app_name | grep "$group_name/$app_name" | tail -$overflow_versions | awk '{print $2}')
  echo "最多保留${max_versions}个版本数据，删除以下多余的版本数据"
  echo $delete_versions

  for delete_version in $delete_versions
  do
    docker rmi $group_name/$app_name:$delete_version
  done
fi

cd ../
sh docker-bin/docker-select.sh
