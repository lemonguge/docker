FROM openjdk:8

ENV JAR_FILE=docker-provider-zk/target/docker-provider-zk-0.0.1-SNAPSHOT.jar

COPY $JAR_FILE application.jar

VOLUME /logs

EXPOSE 8080 8081

ENTRYPOINT ["java","-jar","/application.jar"]
# docker build -t lemonguge/docker-application:1.0 .