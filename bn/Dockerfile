# Fase 1: Compilación
FROM eclipse-temurin:21-jdk AS build
COPY . .

# Damos permisos y ejecutamos Gradle desde la carpeta 'bn'
RUN chmod +x ./bn/gradlew
RUN ./bn/gradlew -p bn clean bootJar -x test

# Fase 2: Ejecución
FROM eclipse-temurin:21-jre
# Copiamos el jar generado por Gradle
COPY --from=build /bn/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
