FROM gradle:latest as builder
WORKDIR /usr/build
ADD ./ .
RUN gradle shadowJar

FROM adoptopenjdk:latest
WORKDIR /usr/src
COPY --from=builder /usr/build/build/libs/Artemis-1.0-SNAPSHOT-all.jar .
CMD ["java", "-jar", "Artemis-1.0-SNAPSHOT-all.jar"]