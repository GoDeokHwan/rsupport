FROM openjdk:17-jdk

EXPOSE 8180
RUN pwd
RUN ls -l
COPY ./build/libs/rsupport-0.0.1-SNAPSHOT.jar /opt/app/rsupport-api.jar

RUN ls -l

CMD ["java", "-jar","/opt/app/rsupport-api.jar", "--spring.profiles.active=docker"]
