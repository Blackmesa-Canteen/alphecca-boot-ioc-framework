#FROM maven:3.8.6-jdk-11-slim AS builder
#
#ADD ./pom.xml pom.xml
#ADD ./src src/
#
## packge clean first, then package
#RUN mvn clean package
#
## mini java 11 runtime
#From adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine

# copy jar to docker to run

#COPY --from=builder target/app.jar app.jar

FROM adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine
ADD ./out/artifacts/app_jar/alphecca-hotel-booking.jar app.jar

ENV PORT=""

# docker build -t hotel-booking-app:v1 .
# docker run -e PORT="8088" hotel-booking-app
CMD java -jar app.jar -port $PORT app



