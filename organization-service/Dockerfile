FROM openjdk:8-jre
WORKDIR /organization-service
EXPOSE 8186
COPY ./target/*.jar /organization-service/organization-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "organization-service-0.0.1-SNAPSHOT.jar" ]