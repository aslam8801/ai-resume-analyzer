# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /build

COPY ai_resume_analyzer/pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY ai_resume_analyzer/src ./src

RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080

CMD ["java","-jar","app.jar"]