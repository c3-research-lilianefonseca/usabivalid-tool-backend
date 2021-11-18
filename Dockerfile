FROM openjdk:11-jdk-oraclelinux8
VOLUME /tmp
COPY target/api-1.0.0-SNAPSHOT.jar api-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","./api-1.0.0-SNAPSHOT.jar"]