FROM openjdk:8-jre
WORKDIR /api-swagger
EXPOSE 8187
COPY ./target/*.jar /api-swagger/api-swagger-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "api-swagger-0.0.1-SNAPSHOT.jar" ]