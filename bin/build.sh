#!/bin/bash

# Usage: sh build.sh
# 因为在开发服务器上会经常更新代码，所以每次都用持续集成构建就不太方便
# 一个解决方案是增量更新代码到服务器上，使用此脚本在服务器上构建

set -e

# 构建目录
BUILD_DIR='project'
# 子项目名称
PROJECT_NAME='pro-main'
# 最多保存版本数
max_versions=5

cd libs
# 当前存在的版本
existed_versions=$(ls -F | grep ".jar" | wc -l)

if [ $existed_versions -gt $max_versions ]; then
  # 删除多余的版本
  overflow_versions=$((existed_versions - max_versions))
  files_to_delete=$(ls -F | grep ".jar" | head -$overflow_versions)
  echo "最多保留${max_versions}个版本数据，删除以下多余的版本数据"
  echo $files_to_delete
  echo ""
  rm -rf $files_to_delete
fi
cd ..

main(){
  # change to build directory
  echo "sh: cd ${BUILD_DIR}"
  cd ${BUILD_DIR}

  # execute script
  chmod +x gradlew
  echo "sh: ./gradlew ${PROJECT_NAME}:build"
  ./gradlew ${PROJECT_NAME}:build

  # move to ../libs
  echo "sh: mv ${PROJECT_NAME}/build/libs/*.jar ../libs"
  mv ${PROJECT_NAME}/build/libs/*.jar ../libs

  # remove distributions
  echo "sh: rm -rf ${PROJECT_NAME}/build/distributions/*"
  rm -rf ${PROJECT_NAME}/build/distributions/*

  # change to root directory
  echo "sh: cd ../"
  cd ../

  echo 'sh: sh select.sh'
  sh select.sh
}

main
