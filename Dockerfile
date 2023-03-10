# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM adoptopenjdk/openjdk8:jdk8u202-b08-alpine-slim

# Copy the jar to the production image from the builder stage.
#COPY --from=builder /app/target/helloworld-*.jar /helloworld.jar
COPY --from=builder /app/target/corona-dashboard-server-*.jar /corona-dashboard-server.jar

# Run the web service on container startup.
CMD ["java","-Xms1800M","-Xmx2048M","-Djava.security.egd=file:/dev/./urandom","-Dhttps.protocols=TLSv1.2,TLSv1.1,TLSv1,SSLv3","-Dserver.port=${PORT}","-Dspring.profiles.active=prd","-jar","/corona-dashboard-server.jar"]"-Xmx2048m"