FROM openjdk:17-jdk-slim-buster
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# docker build --build-arg JAR_FILE=target/*.jar -t myorg/order-service .
# docker run -p 8081:8081 myorg/order-service
