FROM alpine:3.8
MAINTAINER Thomas Deniffel
RUN apk add --no-cache openjdk8
ADD build/libs/bot-0.0.1-SNAPSHOT.jar /bot.jar
ENTRYPOINT java -jar /bot.jar