# FROM openjdk:8-jdk-alpine
#
# ENV JAR_NAME person-service.jar
#
# COPY person-service.jar /home/appuser/person-service.jar

FROM openjdk:8-jdk-alpine
ENV JAR_NAME person-service.jar
COPY person-service.jar /home/appuser/person-service.jar
ENTRYPOINT ["java","-jar","/home/appuser/person-service.jar"]

