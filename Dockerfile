FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package 

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/bh-0.0.1-SNAPSHOT.jar"]
