FROM openjdk:8-jre-alpine

VOLUME /tmp

ARG JAR_FILE=build/libs/sbs-front.jar
ARG PORT=9001
ARG TIME_ZONE=Asia/Shanghai

ENV TZ=${TIME_ZONE}
ENV JAVA_OPTS="-Xms256m -Xmx256m"

COPY ${JAR_FILE} sbs-front.jar

EXPOSE ${PORT}

ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom --spring.profiles.active=prod -server -jar sbs-front.jar
