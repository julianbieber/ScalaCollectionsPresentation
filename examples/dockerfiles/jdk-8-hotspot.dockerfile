FROM openjdk:8-jre-alpine

COPY target/pack /opt/

WORKDIR /opt/bin

RUN chown -R daemon:daemon .

USER daemon
