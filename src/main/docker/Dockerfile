FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD teligen-microservice-consumer-sample.jar teligen-microservice-consumer-sample.jar
RUN sh -c 'touch /teligen-microservice-consumer-sample.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /teligen-microservice-consumer-sample.jar" ]