# STAGE 1
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

COPY settings.gradle build.gradle /app/

COPY src /app/src

RUN gradle build --no-daemon -x test

# STAGE 2
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*jar discografia-1.jar

EXPOSE 443

CMD ["java", "-jar", "discografia-1.jar"]
