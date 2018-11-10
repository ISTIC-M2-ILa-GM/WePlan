FROM openjdk:8-jre
MAINTAINER Gwenole & Gautier

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/weplan/weplan.jar"]

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD target/lib           /usr/share/weplan/lib
# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/weplan/weplan.jar
