# Dockerfile for whole project

# phase-1: compile backend project
FROM maven:3.8.6-jdk-11-slim AS backend-builder

ADD ./src/backend-project/pom.xml pom.xml
ADD ./src/backend-project/src src/

# packge clean first, then package
RUN mvn clean package


# this statement can move the backend jar to a new container
#COPY --from=backend-builder target/app.jar app.jar


####### TEST only, deploy backend to heroku
FROM adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine
COPY --from=backend-builder target/app.jar app.jar

#FROM adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine
#ADD ./out/artifacts/app_jar/alphecca-hotel-booking.jar app.jar

# default export 8088
ENV PORT=""

# docker build -t hotel-booking-app:v1 .
# docker run --name hotel-booking-app -p 0.0.0.0:8088:8088 hotel-booking-app:v1
CMD java -jar app.jar -port $PORT app


######################


