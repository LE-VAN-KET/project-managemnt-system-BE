#FROM eclipse-temurin:8-jdk-alpine as builder
#WORKDIR /member-service
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY ./src ./src
#RUN ./mvnw clean install

FROM eclipse-temurin:8-jre-alpine
WORKDIR /member-service
EXPOSE 8185
COPY target/*.jar /member-service/*.jar
CMD ["java", "-jar", "/member-service/*.jar"]