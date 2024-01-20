# FROM openjdk:17

# # Application must be packaged after changes before building container
# ARG JAR_FILE=*.jar

# WORKDIR /ZPI-BACKEND

# COPY target/${JAR_FILE} application.jar

# EXPOSE 8080

# CMD ["java", "-jar", "application.jar"]
# Stage 1: Build Stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /ZPI-BACKEND

# Copy only the POM file to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application and build it
COPY src src
RUN mvn package -DskipTests

# Stage 2: Runtime Stage
FROM openjdk:17
WORKDIR /ZPI-BACKEND

# Copy the JAR file from the build stage
COPY --from=build /ZPI-BACKEND/target/*.jar application.jar

EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "application.jar"]

