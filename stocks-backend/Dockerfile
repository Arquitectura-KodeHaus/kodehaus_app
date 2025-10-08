# Multi-stage build para Spring Boot

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Construir la aplicación (salta tests para builds más rápidos en CI/CD)
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar el JAR construido desde el stage anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (Cloud Run usa la variable PORT)
EXPOSE 8080

# Variables de entorno opcionales
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando de inicio
# Cloud Run inyecta automáticamente la variable PORT
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
