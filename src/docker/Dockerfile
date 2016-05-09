FROM podbox/openjdk8

RUN useradd podbox
USER podbox
WORKDIR /libs/

CMD java                        \
  -Xmx64m                       \
  -Xss256k                      \
  -server                       \
  -XX:+UseCompressedOops        \
  -Dfile.encoding=UTF-8         \
  -Dspring.profiles.active=$ENV \
  -jar hazelcast-docker.jar
