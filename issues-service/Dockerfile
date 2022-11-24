FROM eclipse-temurin:8-jdk-alpine as builder
WORKDIR /issues-service
COPY settings.xml settings.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY ./src ./src
RUN ./mvnw -s settings.xml clean install -Dmaven.test.skip=true

FROM eclipse-temurin:8-jre-alpine
WORKDIR /issues-service
EXPOSE 8186
COPY --from=builder /issues-service/target/*.jar /issues-service/*.jar
CMD ["java", "-jar", "/issues-service/*.jar"]