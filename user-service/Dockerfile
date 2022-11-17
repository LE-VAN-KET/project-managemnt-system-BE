#FROM eclipse-temurin:8-jdk-alpine as builder
#WORKDIR /user-service
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY ./src ./src
#RUN ./mvnw clean install

FROM eclipse-temurin:8-jre-alpine
WORKDIR /user-service
EXPOSE 8182
COPY target/*.jar /user-service/*.jar
CMD ["java", "-jar", "/user-service/*.jar"]