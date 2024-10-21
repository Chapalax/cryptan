FROM openjdk:17
WORKDIR /app/puller
COPY ./target/puller-1.0-SNAPSHOT.jar puller.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "puller.jar"]