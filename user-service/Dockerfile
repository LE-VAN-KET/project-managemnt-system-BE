FROM openjdk:8-jre
WORKDIR /user-service
EXPOSE 8182
COPY ./target/*.jar /user-service/user-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "user-service-0.0.1-SNAPSHOT.jar" ]