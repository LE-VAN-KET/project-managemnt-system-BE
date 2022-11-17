#ENTRYPOINT [ "java", "-jar", "organization-service-0.0.1-SNAPSHOT.jar" ]
#FROM eclipse-temurin:8-jdk-alpine as builder
#WORKDIR /organization-service
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY ./src ./src
#RUN ./mvnw clean install

FROM eclipse-temurin:8-jre-alpine
WORKDIR /organization-service
EXPOSE 8186
COPY target/*.jar /organization-service/*.jar
CMD ["java", "-jar", "/organization-service/*.jar"]