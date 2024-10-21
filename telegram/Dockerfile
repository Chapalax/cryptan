FROM openjdk:17
ARG bot_token
WORKDIR /app/telegram
COPY ./target/telegram-1.0-SNAPSHOT.jar telegram.jar
ENV BOT_TOKEN=$bot_token
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "telegram.jar"]

