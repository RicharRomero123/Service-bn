# Fase 1: Compilación (Usando JDK 21)
FROM eclipse-temurin:21-jdk-focal AS build
COPY . .

# Damos permisos de ejecución al gradlew y compilamos el proyecto dentro de 'bn'
RUN chmod +x ./bn/gradlew
RUN ./bn/gradlew -p bn clean bootJar -x test

# Fase 2: Ejecución
FROM eclipse-temurin:21-jre-focal
# En Gradle, el archivo se genera en build/libs/
COPY --from=build /bn/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
