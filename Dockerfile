# 以pro-front项目为例
FROM openjdk:8

VOLUME /tmp

ARG JAR_FILE=pro-front/build/libs/sbs-front.jar
ARG PORT=18001
ARG TIME_ZONE=Asia/Shanghai

ENV TZ=${TIME_ZONE}
ENV JAVA_OPTS="-Xms256m -Xmx256m"
ENV SERVER_ENV=dev

COPY ${JAR_FILE} sbs-front.jar

EXPOSE ${PORT}

ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -server -jar sbs-front.jar --spring.profiles.active=${SERVER_ENV}
