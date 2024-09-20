FROM maven:3.9.9-amazoncorretto-21-debian AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true

FROM amazoncorretto:21-alpine AS runtime
WORKDIR /app
ARG JAR_FILE=/app/target/api-gateway-0.0.1-SNAPSHOT.jar
COPY --from=build $JAR_FILE /app/api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
EXPOSE 8080