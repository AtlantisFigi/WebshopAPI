# Build stage
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY checkstyle.xml .
RUN mvn dependency:go-offline # Cache dependencies
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:22-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/WebshopAPI-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
