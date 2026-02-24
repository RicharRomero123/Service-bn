# Fase 1: Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
# Copiamos el contenido de la carpeta bn al contenedor
COPY . .
# Damos permisos y ejecutamos el build de Gradle
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test

# Fase 2: Ejecución
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
