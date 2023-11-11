FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} installment-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/installment-service-0.0.1-SNAPSHOT.jar"]