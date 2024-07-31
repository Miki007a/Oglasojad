FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/e-shop-0.0.1-SNAPSHOT.jar /app/e-shop-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/e-shop-0.0.1-SNAPSHOT.jar.jar"]
