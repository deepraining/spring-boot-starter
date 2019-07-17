# spring-boot-starter

[中文文档](./README.zh.md)

A boilerplate for creating a [Spring Boot](https://spring.io/projects/spring-boot) application, using [Gradle](https://gradle.org/).

## Features

- Use [Gradle](https://gradle.org/) to build the whole project.
- Support multiple sub projects, and you can add infinite custom sub projects as needed.
- Use [CheckStyle](https://checkstyle.org/) to check whether Java codes are written compliantly with specified rules, and use [google-java-format](https://github.com/google/google-java-format) to optimize Java codes' format.
- Use [Git pre-commit hook](./config/hooks) to pre-checking Java codes before `git commit`. If there are some Java codes which are not compliant with specified rules, Git commit will fail.
- Use [Flyway](https://flywaydb.org/) to manage database tables' versions.
- Completely separate frontend and backend, and this project is only for backend Java codes. Frontend codes need another project.
- Use [Mybatis](https://www.mybatis.org/) as database connection layer, and use [Mybatis Generator](http://www.mybatis.org/generator/) to automatically generate model files.
- Use `uuid` to substitute `id`, and to avoid malicious accessing of data in database.
- Deeply integrate with [Swagger](https://swagger.io/), to conveniently generate API documentation and provide data-mocking service.

## Runtime Environment

- Mysql: 8+ (some sql sentences cant be run in `5.x`)

## Some restraint

- All API should start with `/api/`.

## Gradle Tasks created by this project

### checkJava

Check whether Java codes are written compliantly with specified rules.

```
./gradlew checkJava 
```

### installGitHooks

Install Git `pre-commit` Hook.

```
./gradlew installGitHooks 
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

Install Git `pre-commit` Hook.

```
./gradlew installGitHooks 
```

Create database and tables.

```
# For local environment: default config file is flyway.conf
flyway migrate

# For online test environment
flyway migrate -configFiles=flyway-test.conf

# For online production environment
flyway migrate -configFiles=flyway-prod.conf

# If you want more environments, you can add one by yourself 
```

Generate Mybatis model files.

```
./gradlew pro-gen:run
```

## Run project

Develop and debug `pro-demo` sub project in local environment, then open `http://localhost:9000` in browser.

```
./gradlew pro-demo:bootRun
```

If your IDE does not provide the functionality of watching Java files' changes, you can use command blow.

```
./gradlew pro-demo:watchJava -t
```

Build this project when finish developing.

```
./gradlew pro-demo:build
```

## Deploy project

Create a `serverDirName` directory in a proper place of server machine.

Upload `pro-demo/build/libs/demo-latest.jar, bin/demo-*.sh` in local to `serverDirName` directory in server machine,
and also upload `html` files of frontend to `serverDirName/templates` directory.

```
- serverDirName/
  - demo-test.sh
  - demo-prod.sh
  - demo-latest.jar
  - templates/
```

Related commands of running (take `demo-prod.sh` for example).

```
cd serverDirName

sh demo-prod.sh start        # start running
sh demo-prod.sh stop         # stop running
sh demo-prod.sh restart      # restart running
sh demo-prod.sh status       # see the status
```

## Referred projects

- [mall](https://github.com/macrozheng/mall)
- [halo](https://github.com/halo-dev/halo)
