FROM azul/zulu-openjdk-alpine:21

WORKDIR /root/bot

COPY target/mastodonweatherbot-1.0-SNAPSHOT.jar .
COPY src/main/scripts/start-bot.sh .

RUN touch crontab.tmp \
    && echo '*/2 * * * * /root/bot/start-bot.sh' > crontab.tmp \
    && crontab crontab.tmp \
    && rm -rf crontab.tmp

CMD ["/usr/sbin/crond", "-f", "-d", "0"]
