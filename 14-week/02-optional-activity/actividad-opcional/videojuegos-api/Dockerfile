# ---------- Etapa 1: compilar el proyecto y generar el .jar ----------
# Esta imagen trae Maven y el JDK 17 (la misma version de Java del proyecto)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
# Las pruebas ya se corren con "mvn test"; aqui solo se empaqueta
RUN mvn -q package -DskipTests

# ---------- Etapa 2: imagen final, solo con lo necesario para ejecutar ----------
# JRE = solo el entorno para correr Java (sin Maven ni el codigo fuente): imagen mas liviana
FROM eclipse-temurin:17-jre
COPY --from=build /build/target/videojuegos-api-1.0.0.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
