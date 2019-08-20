# spring-boot-starter

[English Documentation](./README.en.md)

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

## 子项目

- `pro-common`: 通用代码
- `pro-mbg`: Mybatis Generator
- `pro-demo`: 使用 Session 保持登陆状态的 Demo 应用
- `pro-jwtdemo`: 使用 JWT 保持登陆状态的 Demo 应用

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
# 本地环境：默认 flyway.conf 配置文件
flyway migrate

# 线上测试环境
flyway migrate -configFiles=flyway-test.conf

# 线上产品环境
flyway migrate -configFiles=flyway-prod.conf

# 如果你需要配置更多的环境，可以自己添加
```

执行自动生成 Mybatis 模型文件的命令

```
./gradlew pro-mbg:run
```

## 运行项目

以 `pro-demo` 为例:

执行本地开发调试 `pro-demo` 子项目的命令，然后在浏览器中打开 `http://localhost:9000`

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

以 `pro-demo` 为例:

在服务器上，找个合适的地方创建 `serverDirName` 目录

把本地 `pro-demo/build/libs/demo-latest.jar, pro-demo/bin/*.sh` 上传到 `serverDirName` 目录，
并把前端的 `html` 文件都上传到 `serverDirName/templates` 目录下

```
- serverDirName/
  - test.sh
  - prod.sh
  - demo-latest.jar
  - templates/
```

运行程序相关命令（以 `prod.sh` 为例）

```
cd serverDirName

sh prod.sh start        # 运行程序
sh prod.sh stop         # 停止程序
sh prod.sh restart      # 重启程序
sh prod.sh status       # 查看程序状态
```

## 参考项目

- [mall](https://github.com/macrozheng/mall)
- [halo](https://github.com/halo-dev/halo)
