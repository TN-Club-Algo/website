FROM eclipse-temurin:17

LABEL author="Alexandre Duchesne"

# Add main jar
COPY build/libs/website.jar /app.jar

VOLUME /tmp
VOLUME /var/algotn
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]