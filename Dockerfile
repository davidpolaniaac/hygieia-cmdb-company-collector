FROM openjdk:8-jre

MAINTAINER Hygieia@capitalone.com

RUN mkdir /hygieia /hygieia/config

COPY target/*.jar /hygieia

COPY docker/properties-builder.sh /hygieia/

RUN chmod +x /hygieia/properties-builder.sh

WORKDIR /hygieia

VOLUME ["/hygieia/logs"]

ENV PROP_FILE /hygieia/config/application.properties

CMD ./properties-builder.sh && \
  java -Djava.security.egd=file:/dev/./urandom -jar company*.jar --spring.config.location=$PROP_FILE