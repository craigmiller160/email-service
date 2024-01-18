FROM openjdk:21-slim

COPY build/email-service-*.jar /email-service.jar

ENTRYPOINT ["java", "-jar", "email-service.jar"]