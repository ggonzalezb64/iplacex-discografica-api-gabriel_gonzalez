# STAGE 1
FROM gradle:7.6-jdk21 AS builder

WORKDIR /app

COPY gradlew settings.gradle build.gradle /app/
COPY gradle /app/gradle

RUN chmod +x ./gradlew

RUN ./gradlew dependencies --no-daemon || true

COPY src /app/src

RUN ./gradlew build --no-daemon -x test

# STAGE 2
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*jar discografia-1.jar

EXPOSE 443

CMD ["java", "-jar", "discografia-1.jar"]