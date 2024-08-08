FROM azul/zulu-openjdk-alpine:21

WORKDIR /root/bot


COPY target/mastodonweatherbot-1.0-SNAPSHOT.jar .
COPY src/main/scripts/start-bot.sh .

ENTRYPOINT ["sh", "-c", "./start-bot.sh"]
