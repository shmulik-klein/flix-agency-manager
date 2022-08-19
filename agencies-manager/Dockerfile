FROM gradle:7.5-jdk11-alpine as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace

FROM eclipse-temurin:11-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /builder/build/libs/agency-manager-server-0.0.1-SNAPSHOT.jar agency-manager-server.jar
CMD ["java", "-jar", "agency-manager-server.jar"]
