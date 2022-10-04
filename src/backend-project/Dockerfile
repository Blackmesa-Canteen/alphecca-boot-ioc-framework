FROM maven:3.8.6-jdk-11-slim AS builder

ADD ./pom.xml pom.xml
ADD ./src src/

# packge clean first, then package
RUN mvn clean package

# mini java 11 runtime
FROM adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine

# copy jar to docker to run

COPY --from=builder target/app.jar app.jar

#FROM adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine
#ADD ./out/artifacts/app_jar/alphecca-hotel-booking.jar app.jar

# default export 8088
ENV PORT=$port
EXPOSE $PORT

# docker build -t hotel-booking-app:v1 .
# docker run --name hotel-booking-app -p 0.0.0.0:8088:8088 hotel-booking-app:v1
CMD java -Xms128m -Xmx256m -XX:PermSize=64M -XX:MaxPermSize=128M -jar app.jar -port $PORT



