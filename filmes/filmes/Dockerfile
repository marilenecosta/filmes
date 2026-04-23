FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src
RUN chmod 777 mvnw
RUN ./mvnw package -DskipTests
RUN ls -l ./target
CMD ["java", "-jar", "target/filmes-0.0.1-SNAPSHOT.jar"]