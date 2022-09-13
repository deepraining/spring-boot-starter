# spring-boot-starter

用于快速创建 [Spring Boot](https://spring.io/projects/spring-boot) 应用的模板脚手架，使用 [Gradle](https://gradle.org/) 构建项目。

## 特性

- 使用 [Gradle](https://gradle.org/) 构建整个项目
- 多项目模式，可以根据需要自行添加多个子项目
- 使用 [CheckStyle](https://checkstyle.org/) 检查 Java 代码规范，使用 [google-java-format](https://github.com/google/google-java-format) 优化 Java 代码格式
- 使用 [Git pre-commit hook](./config/hooks) 在 `git commit` 之前对代码预检，如果有不符合规范的代码，将不予提交
- 使用 [Flyway](https://flywaydb.org/) 进行数据库版本化管理
- 前后端完全分离，本项目将只用于写后端 Java 代码，前端代码需要另外建立一个项目
- 使用 [Mybatis](https://www.mybatis.org/) 作为数据库链接层，使用 [Mybatis Generator](http://www.mybatis.org/generator/) 自动生成模型文件
- 深度整合 [Swagger](https://swagger.io/)，便于生成 API 接口文档和数据 Mock 服务
- 使用 [snowflake](https://github.com/twitter-archive/snowflake) 算法生成分布式唯一 ID
- 支持 [docker](https://www.docker.com/) 部署

## 子项目

- `pro-common`: 通用代码
- `pro-mbg`: Mybatis Generator
- `pro-admin`: 使用 JWT 保持登陆状态的后台管理应用，包括基于角色的访问控制（RBAC），示例前端项目 [sbs-admin-web](https://github.com/deepraining/sbs-admin-web)
- `pro-front`: 使用 Session-Cookie 保持登陆状态的前端应用
- `pro-search`: 使用 [ElasticSearch](https://www.elastic.co/) 来做文本搜索引擎
- `pro-wx`: 微信登录相关示例项目

## 扩展 Gradle Tasks

### checkJava

用于检查整个项目的 Java 代码是否符合规范

```
./gradlew checkJava 
```

### pro-*:watchJava

本地开发时，用于监听 Java 文件变动，然后自动编译到 `build` 目录下（有些集成开发工具已经自带这个功能），自动刷新应用

(配合 `spring-boot-devtools` 一起用)

```
./gradlew pro-*:watchJava -t
```

## 创建项目

克隆代码，然后根据需要调整项目与代码

```
git clone https://github.com/deepraining/spring-boot-starter.git yourProName --depth=1

cd yourProName
```

去掉原有的 Git 信息，并重新初始化

```
rm -rf .git

git init
```

创建数据库与表结构（数据表文件在 `sql` 目录下，可以自行修改）

```
# 本地环境：默认 flyway.conf 配置文件
flyway migrate

# 线上环境
flyway migrate -configFiles=flyway-prod.conf

# 如果你需要配置更多的环境，可以自己添加
```

执行自动生成 `Mybatis` 模型文件的命令

```
./gradlew pro-mbg:run
```

## 运行项目

以 `pro-admin` 为例:

执行本地开发调试 `pro-admin` 子项目的命令，然后在浏览器中打开 `http://127.0.0.1:9000`

```
./gradlew pro-admin:bootRun
```

如果集成开发工具不支持监听 Java 文件变动并自动编译的功能，可以运行下面的命令

```
./gradlew pro-admin:watchJava -t
```

开发完毕之后执行构建(可以使用 shell 脚本把已经构建好的 html 文件复制到 `resources/templates` 目录下)

```
./gradlew pro-admin:build
```

## 部署项目

以 `pro-admin` 为例:

在服务器上，找个合适的地方创建 `serverDirName` 目录（`serverDirName` 为使用者自定义目录）

把本地 `pro-admin/build/libs/*.jar, bin/*.sh` 上传到 `serverDirName` 目录，并按实际需要修改 `run.sh, select.sh` 中 `SERVER_ENV` 与 `ARCHIVE_NAME` 变量的值

```
- serverDirName/
  - run.sh              # 运行、停止、重启、查看程序
  - select.sh           # 运行最新版本的 jar 文件
  - libs
    - sbs-admin-2020.0501.1001.jar
    - sbs-admin-2020.0501.1101.jar
    - ...
```

```
cd serverDirName

sh run.sh start        # 运行程序
sh run.sh stop         # 停止程序
sh run.sh restart      # 重启程序
sh run.sh status       # 查看程序状态
sh run.sh version      # 查看程序版本
```

## 前端与后端分开部署(html 文件不打包进 jar 文件)

`application.yml`: 

```
spring:
+ thymeleaf:
+   prefix: file:./templates/
+   cache: false
```

## 配置文件与 jar 文件分开部署(yml 文件不打包进 jar 文件)

你可以把配置文件部署到 `~/.sbs-admin/application*.yml`，就可以覆盖 `src/main/resources/application*.yml` 的配置

## Docker 部署

参看 [pro-admin Dockerfile](./pro-admin/Dockerfile)

## 参考项目

- [mall](https://github.com/macrozheng/mall)
- [halo](https://github.com/halo-dev/halo)

## 注意

- 使用 `pro-search` 时，ElasticSearch 服务器与客户端版本最好严格一致，本项目的客户端版本是 `v6.4.3`
