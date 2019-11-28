<<<<<<< HEAD
FROM gradle:latest as builder
WORKDIR /usr/build
ADD ./ .
RUN gradle shadowJar

FROM adoptopenjdk:latest
WORKDIR /usr/src
COPY --from=builder /usr/build/build/libs/Artemis-1.0-SNAPSHOT-all.jar .
CMD ["java", "-jar", "Artemis-1.0-SNAPSHOT-all.jar"]
=======
FROM debian:latest as debian

RUN apt-get update -y
RUN apt-get install openjdk-11-jdk unzip wget -y
ADD build/libs/Artemis-1.0-SNAPSHOT-all.jar Artemis-1.0-SNAPSHOT-all.jar
CMD java -jar Artemis-1.0-SNAPSHOT-all.jar
>>>>>>> fe8513cfd4f4f19dda94f041fd00f221b334d54e
