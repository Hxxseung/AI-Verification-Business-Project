# server base image - java 17
FROM eclipse-temurin:17-alpine

# copy .jar file to docker
COPY ./build/libs/aibusiness-0.0.1-SNAPSHOT.jar app.jar

# always do command
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]