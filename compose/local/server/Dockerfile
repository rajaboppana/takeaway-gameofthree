FROM openjdk:11.0-jdk


LABEL maintainer="mosalam208@gmail.com"

COPY ./game-of-three-api/target/game-of-three-api-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "game-of-three-api-0.0.1-SNAPSHOT.jar"]

