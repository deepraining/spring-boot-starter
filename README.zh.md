# spring-boot-starter

[English Documentation](./README.md)

用于快速创建 [Spring Boot](https://spring.io/projects/spring-boot) 应用的模板脚手架，使用 [Gradle](https://gradle.org/) 构建项目。

## 特性

- 使用 [Gradle](https://gradle.org/) 构建整个项目
- 多项目模式，可以根据需要自行添加多个子项目
- 使用 [CheckStyle](https://checkstyle.org/) 检查 Java 代码规范，使用 [google-java-format](https://github.com/google/google-java-format) 优化 Java 代码格式
- 使用 [Git pre-commit hook](./config/hooks) 在 `git commit` 之前对代码预检，如果有不符合规范的代码，将不予提交
- 使用 [Flyway](https://flywaydb.org/) 进行数据库版本化管理
- 前后端完全分离，本项目将只用于写后端 Java 代码，前端代码需要另外建立一个项目
- 使用 [Mybatis](https://www.mybatis.org/) 作为数据库链接层，使用 [Mybatis Generator](http://www.mybatis.org/generator/) 自动生成模型文件
- 使用 `uuid` 代替 `id`，可以防止数据被恶意遍历获取
- 深度整合 [Swagger](https://swagger.io/)，便于生成 API 接口文档和数据 Mock 服务

## 运行环境

- Mysql: 8+ (有些语句在 `5.x` 的版本下不能运行)

## 一些约束

- 所有的接口都要以 `/api/` 开头

## 扩展 Gradle Tasks

### checkJava

用于检查整个项目的 Java 代码是否符合规范

```
./gradlew checkJava 
```

### installGitHooks

安装 Git `pre-commit` Hook

```
./gradlew installGitHooks 
```

### pro-*:watchJava

用于监听 Java 文件变动，然后自动编译到 `build` 目录下（有些集成开发工具已经自带这个功能）

(配合 `spring-boot-devtools` 一起用)

```
./gradlew pro-*:watchJava -t
```

## 创建项目

克隆代码，然后根据需要调整项目与代码

```
git clone https://github.com/senntyou/spring-boot-starter.git yourProName --depth=1

cd yourProName
```

去掉原有的 Git 信息，并重新初始化

```
rm -rf .git

git init
```

安装 Git `pre-commit` Hook

```
./gradlew installGitHooks 
```

创建数据库与表结构

```
# 默认 flyway.conf 配置文件
flyway migrate

# 产品环境 flyway-prod.conf 配置文件
flyway migrate -configFiles=flyway-prod.conf
```

执行自动生成 Mybatis 模型文件的命令

```
./gradlew pro-gen:run
```

## 运行项目

执行本地开发调试 `pro-demo` 子项目的命令

```
./gradlew pro-demo:bootRun
```

如果集成开发工具不支持监听 Java 文件变动并自动编译的功能，可以运行下面的命令

```
./gradlew pro-demo:watchJava -t
```

开发完毕之后执行构建

```
./gradlew pro-demo:build
```

## 部署项目

在服务器上，找个合适的地方创建 `serverDirName` 目录

把本地 `pro-demo/build/libs/demo-latest.jar, bin/demo.sh` 上传到 `serverDirName` 目录，
并把前端的 `html` 文件都上传到 `serverDirName/templates` 目录下

```
- serverDirName/
  - demo.sh
  - demo-latest.jar
  - templates/
```

运行程序相关命令

```
cd serverDirName

sh demo.sh start        # 运行程序
sh demo.sh stop         # 停止程序
sh demo.sh restart      # 重启程序
sh demo.sh status       # 查看程序状态
```

## 参考项目

- [mall](https://github.com/macrozheng/mall)
- [halo](https://github.com/halo-dev/halo)
