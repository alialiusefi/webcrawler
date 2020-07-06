FROM gradle:6.5.0-jdk11 AS build
#COPY --chown=gradle:gradle . /home/gradle/src
ENV APP_PATH=/home/gradle/src
WORKDIR $APP_PATH
#RUN gradle bootJar

FROM openjdk:11-jre-slim

COPY application/build/libs/application-0.0.1-SNAPSHOT.jar ./

EXPOSE 8080
EXPOSE 5432

cmd java -jar application-0.0.1-SNAPSHOT.jar