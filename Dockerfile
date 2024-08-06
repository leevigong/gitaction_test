FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","/app.jar"]