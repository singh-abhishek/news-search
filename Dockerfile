FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/NewsSearch-1.0.jar .

EXPOSE 8080

CMD ["java", "-jar", "NewsSearch-1.0.jar"]
