FROM adoptopenjdk/openjdk8-openj9

COPY target/pack /opt/

WORKDIR /opt/

RUN chown -R daemon:daemon .

USER daemon

ENTRYPOINT /opt/bin/main