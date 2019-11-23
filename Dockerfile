FROM debian:latest as debian

RUN apt-get update -y
RUN apt-get install openjdk-11-jdk unzip wget -y
ADD Artemis-1.0-SNAPSHOT-all.jar build/libs/Artemis-1.0-SNAPSHOT-all.jar
CMD java -jar Artemis-1.0-SNAPSHOT-all.jar
