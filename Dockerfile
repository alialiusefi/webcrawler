FROM openjdk:11-jre-slim AS build

COPY application/build/libs/application-0.0.1-SNAPSHOT.jar ./

EXPOSE 8080

cmd java -jar application-0.0.1-SNAPSHOT.jar