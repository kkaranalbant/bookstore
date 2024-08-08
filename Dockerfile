FROM openjdk:17-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["/bin/sh", "-c", "sleep 20 && exec java -jar /app.jar"]
