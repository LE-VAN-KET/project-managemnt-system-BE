FROM eclipse-temurin:8-jdk-alpine as builder
WORKDIR /organization-service
COPY settings.xml settings.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY ./src ./src
RUN ./mvnw -s settings.xml clean install -Dmaven.test.skip=true

FROM eclipse-temurin:8-jre-alpine
WORKDIR /organization-service
EXPOSE 8186
COPY --from=builder /organization-service/target/*.jar /organization-service/*.jar
CMD ["java", "-jar", "/organization-service/*.jar"]