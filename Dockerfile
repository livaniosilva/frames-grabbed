FROM openjdk:11-jre
MAINTAINER livanio.silva2@gmail.com
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/frame.jar
WORKDIR /app
ENTRYPOINT java -jar frame.jar