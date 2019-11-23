# spring-boot-starter

[中文文档](./README.md)

A boilerplate for creating a [Spring Boot](https://spring.io/projects/spring-boot) application, using [Gradle](https://gradle.org/).

## Features

- Use [Gradle](https://gradle.org/) to build the whole project.
- Support multiple sub projects, and you can add infinite custom sub projects as needed.
- Use [CheckStyle](https://checkstyle.org/) to check whether Java codes are written compliantly with specified rules, and use [google-java-format](https://github.com/google/google-java-format) to optimize Java codes' format.
- Use [Git pre-commit hook](./config/hooks) to pre-checking Java codes before `git commit`. If there are some Java codes which are not compliant with specified rules, Git commit will fail.
- Use [Flyway](https://flywaydb.org/) to manage database tables' versions.
- Completely separate frontend and backend, and this project is only for backend Java codes. Frontend codes need another project.
- Use [Mybatis](https://www.mybatis.org/) as database connection layer, and use [Mybatis Generator](http://www.mybatis.org/generator/) to automatically generate model files.
- Deeply integrate with [Swagger](https://swagger.io/), to conveniently generate API documentation and provide data-mocking service.
- Use [snowflake](https://github.com/twitter-archive/snowflake) algorithm to generate distributed unique id.
- Support [docker](https://www.docker.com/) deployment.

## Sub projects

- `pro-common`: Common codes to share between sub projects
- `pro-mbg`: Mybatis Generator
- `pro-demo`: Demo application using session to remember logged-in status
- `pro-jwtdemo`: Demo application using JWT to remember logged-in status

## Gradle Tasks created by this project

### checkJava

Check whether Java codes are written compliantly with specified rules.

```
./gradlew checkJava 
```

### pro-*:watchJava

Watch Java files' changes, and automatically compile them to `build` directory (some IDE also provide this feature).

(work with `spring-boot-devtools`)

```
./gradlew pro-*:watchJava -t
```

## Create project

Clone this project, then modify codes and project as needed.

```
git clone https://github.com/senntyou/spring-boot-starter.git yourProName --depth=1

cd yourProName
```

Remove existed git information and re-init it.

```
rm -rf .git

git init
```

Create database and tables.

```
# For local environment: default config file is flyway.conf
flyway migrate

# For online environment
flyway migrate -configFiles=flyway-prod.conf

# If you want more environments, you can add one by yourself 
```

Generate Mybatis model files.

```
./gradlew pro-mbg:run
```

## Run project

Take `pro-demo` for example:

Develop and debug `pro-demo` sub project in local environment, then open `http://127.0.0.1:9000` in browser.

```
./gradlew pro-demo:bootRun
```

If your IDE does not provide the functionality of watching Java files' changes, you can use command blow.

```
./gradlew pro-demo:watchJava -t
```

Build this project when finish developing(you can copy html files which built by web tools to `resources/templates` directory by shell script).

```
./gradlew pro-demo:build
```

## Deploy project

Take `pro-demo` for example:

Create a `serverDirName` directory in a proper place of server machine.

Upload `pro-demo/build/libs/*.jar, bin/run.sh` in local to `serverDirName` directory in server machine.

```
- serverDirName/
  - run.sh              # you can modify the value of variable SERVER_ENV according to the reality
  - libs　
    - *.jar
```

```
cd serverDirName

sh run.sh start        # start running
sh run.sh stop         # stop running
sh run.sh restart      # restart running
sh run.sh status       # see the status
```

## Deploy html files separately(not in jar files)

`application.yml`: 

```
spring:
  thymeleaf:
-   prefix: classpath:templates/
+   prefix: file:./templates/
+   cache: false
```

## Deploy `application*.yml` config files separately(not in jar files)

You can deploy config files into `~/.sbs-demo/application*.yml`, and it will override `src/main/resources/application*.yml`. 

## Docker deployment

See [pro-demo Dockerfile](./pro-demo/Dockerfile).

## Referred projects

- [mall](https://github.com/macrozheng/mall)
- [halo](https://github.com/halo-dev/halo)
