FROM maven:3.9.0-eclipse-temurin-19 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:19-ea-oracle
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN groupadd app && useradd -g app -s /bin/bash -m app
RUN chown app:app /app/app.jar
USER app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]