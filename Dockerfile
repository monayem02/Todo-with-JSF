# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2
FROM ghcr.io/eclipse-ee4j/glassfish:7.0.18

# Copy JSF WAR file to the autodeploy folder in GlassFish
COPY --from=builder /app/target/todo.war /opt/glassfish7/glassfish/domains/domain1/autodeploy/
# Copy postgres jdbc jar file to the lib folder in GlassFish
COPY postgresql-42.7.4.jar /opt/glassfish7/glassfish/domains/domain1/lib/

# Start GlassFish server when the container starts
CMD ["asadmin", "start-domain", "--verbose", "domain1"]