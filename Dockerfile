FROM openjdk:17
COPY tle/tle-application/build/libs/tle-application-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar","/tle-application-0.0.1-SNAPSHOT.jar"]