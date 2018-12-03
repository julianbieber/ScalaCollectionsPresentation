FROM openjdk:8-jre-alpine

COPY target/pack /opt/

WORKDIR /opt/

RUN chown -R daemon:daemon .

USER daemon

ENTRYPOINT /opt/bin/main