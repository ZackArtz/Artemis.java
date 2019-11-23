FROM debian:latest as debian

RUN apt-get update -y
RUN apt-get install openjdk-11-jdk unzip wget -y
ADD build/libs/Artemis-1.0-SNAPSHOT-all.jar Artemis-1.0-SNAPSHOT-all.jar
CMD java -jar Artemis-1.0-SNAPSHOT-all.jar
