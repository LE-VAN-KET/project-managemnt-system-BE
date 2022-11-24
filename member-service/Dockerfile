FROM eclipse-temurin:8-jdk-alpine as builder
WORKDIR /member-service
COPY settings.xml settings.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY ./src ./src
RUN ./mvnw -s settings.xml clean install -Dmaven.test.skip=true

FROM eclipse-temurin:8-jre-alpine
WORKDIR /member-service
EXPOSE 8186
COPY --from=builder /member-service/target/*.jar /member-service/*.jar
CMD ["java", "-jar", "/member-service/*.jar"]