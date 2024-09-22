# Build stage with OpenJDK 17
FROM openjdk:17 as build

WORKDIR /workspace/app

# Copy application files
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
COPY src src/

# Make the mvnw script executable and run it
RUN chmod +x mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Final stage with a slimmer image
FROM openjdk:17-slim

WORKDIR /app

# Fix DNS resolution issues
RUN echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf

# Set DNS cache to 10 seconds if the security file exists
RUN if [ -f /usr/lib/jvm/java-17-openjdk/jre/lib/security/java.security ]; then \
        grep '^networkaddress.cache.ttl=' /usr/lib/jvm/java-17-openjdk/jre/lib/security/java.security || echo 'networkaddress.cache.ttl=10' >> /usr/lib/jvm/java-17-openjdk/jre/lib/security/java.security; \
    fi

# Copy the built JAR from the build stage
COPY --from=build /workspace/app/target/e-shop-0.0.1-SNAPSHOT.jar /app/e-shop-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/e-shop-0.0.1-SNAPSHOT.jar"]

