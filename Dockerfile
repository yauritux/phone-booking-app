FROM maven:3.8.3-openjdk-17 AS builder
MAINTAINER yauritux@gmail.com

ENV HOME=/usr/app/phone-booking-app
RUN mkdir -p $HOME
WORKDIR $HOME

ADD pom.xml $HOME
ADD phone-booking-core/pom.xml $HOME/phone-booking-core/pom.xml
ADD phone-booking-restws/pom.xml $HOME/phone-booking-restws/pom.xml

RUN mvn -pl phone-booking-core verify --fail-never
ADD phone-booking-core $HOME/phone-booking-core
RUN mvn -DskipTests -pl phone-booking-core install
RUN mvn -pl phone-booking-restws verify --fail-never
ADD phone-booking-restws $HOME/phone-booking-restws
RUN mvn -DskipTests -pl phone-booking-core,phone-booking-restws install

FROM openjdk:17.0-jdk-slim

WORKDIR /app

COPY --from=builder /usr/app/phone-booking-app/phone-booking-restws/target/phone-booking-restws-1.0.jar ./phone-booking-restws-1.0.jar

CMD ["java", "-jar", "/app/phone-booking-restws-1.0.jar"]