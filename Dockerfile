# STAGE 1: Compilación usando Gradle 8 con JDK 21
FROM gradle:8-jdk21 AS builder

WORKDIR /app

COPY gradlew settings.gradle build.gradle /app/
COPY gradle /app/gradle

RUN ./gradlew dependencies --no-daemon || true

COPY src /app/src

RUN ./gradlew build --no-daemon -x test

# STAGE 2
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*jar discografia-1.jar

EXPOSE 443

CMD ["java", "-jar", "discografia-1.jar"]