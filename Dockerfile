FROM debian:latest as debian

RUN apt-get update -y
RUN apt-get install openjdk-11-jdk unzip wget -y
ENTRYPOINT ["java", "-jar", "build/libs/Artemis-1.0-SNAPSHOT-all.jar"]
