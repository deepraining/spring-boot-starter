#!/bin/bash

# Usage: sh build.sh
# 因为在开发服务器上会经常更新代码，所以每次都用持续集成构建就不太方便
# 一个解决方案是增量更新代码到服务器上，使用此脚本在服务器上构建

set -e

# 构建目录
BUILD_DIR='project'
# 子项目名称
PROJECT_NAME='pro-main'

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

  # change to root directory
  echo "sh: cd ../"
  cd ../

  echo 'sh: sh select.sh'
  sh select.sh
}

main
