# Dockerfile multi-stage para Spring Boot + Gradle
# Optimizado para despliegue en Render

# Stage 1: Build
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Copiar archivos de configuración de Gradle primero (mejor uso de caché)
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle/

# Descargar dependencias (esta capa se cachea si no cambian las dependencias)
RUN gradle dependencies --no-daemon || true

# Copiar código fuente
COPY src src/

# Compilar aplicación y generar JAR ejecutable
RUN gradle bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar solo el JAR compilado desde la stage anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer puerto 8080 (puerto por defecto de Spring Boot)
EXPOSE 8080

# Variables de entorno opcionales (pueden ser sobrescritas en Render)
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=prod

# Ejecutar aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
