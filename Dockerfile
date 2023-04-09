# Build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /home/app
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean test package
# Package stage
FROM openjdk:17-alpine
WORKDIR /home/app
COPY --from=build /home/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]