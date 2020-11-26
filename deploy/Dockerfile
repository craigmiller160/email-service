FROM openjdk:15.0.1-jre-slim

COPY build/email-service-*.jar /email-service.jar

ENTRYPOINT ["java", "-jar", "--enable-preview", "email-service.jar"]