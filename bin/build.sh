#!/bin/bash

# Usage: sh build.sh
# 构建并本地运行(以pro-front项目为例)

# 因为在开发服务器上会经常更新代码，所以每次都用持续集成构建就不太方便
# 一个解决方案是增量更新代码到服务器上，使用此脚本在服务器上构建

set -e

# 构建目录
build_dir='project'
# 子项目名称
project_name='pro-front'
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

# change to build directory
echo "sh: cd ${build_dir}"
cd ${build_dir}

# execute script
chmod +x gradlew
echo "sh: ./gradlew ${project_name}:build"
./gradlew ${project_name}:build

# move to ../libs
echo "sh: mv ${project_name}/build/libs/*.jar ../libs"
mv ${project_name}/build/libs/*.jar ../libs

# remove distributions
echo "sh: rm -rf ${project_name}/build/distributions/*"
rm -rf ${project_name}/build/distributions/*

# change to root directory
echo "sh: cd ../"
cd ../

echo 'sh: sh bin/select.sh'
sh bin/select.sh
