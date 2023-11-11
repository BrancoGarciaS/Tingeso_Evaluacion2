FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} student-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/student-service-0.0.1-SNAPSHOT.jar"]