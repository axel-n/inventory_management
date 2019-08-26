FROM openjdk:11
VOLUME /tmp

COPY target/inventory_management-1.0-SNAPSHOT.jar /opt/app.jar

WORKDIR /opt

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
