FROM gradle:8.13-jdk21 AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle clean bootJar --no-daemon

FROM openjdk:21
COPY --from=builder /app/build/libs/*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]