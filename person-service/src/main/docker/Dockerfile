# FROM openjdk:8-jdk-alpine
#
# ENV JAR_NAME ${artifactName}.jar
#
# COPY ${artifactName}.jar /home/appuser/${artifactName}.jar

FROM openjdk:8-jdk-alpine
ENV JAR_NAME ${artifactName}.jar
COPY ${artifactName}.jar /home/appuser/${artifactName}.jar
ENTRYPOINT ["java","-jar","/home/appuser/${artifactName}.jar"]

