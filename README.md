# 🏢 PlazApp - Sistema SaaS para Gestión de Plazas de Mercado

Plataforma SaaS multi-tenant construida con **Spring Boot** (backend) y **Angular** (frontend), pensada para desplegarse en **Google Cloud Platform (GCP)**.

## 🎯 Acerca del Proyecto

PlazApp es un sistema multi-servicio diseñado para gestionar plazas de mercado bajo un modelo SaaS. El sistema permite al dueño del software administrar múltiples plazas, sus gerentes, suscripciones y módulos habilitados.

### Servicios Planificados

1. **🏢 Servicio del Sistema (Dueño del software)** ✅ **IMPLEMENTADO**
   - Gestión de plazas y gerentes
   - Administración de suscripciones y planes
   - Control de módulos habilitados por plaza
   - Facturación y pagos

2. **🏬 Servicio de Plaza** (Próximamente)
   - Gestión de información de la plaza
   - Administración de usuarios (comerciantes, empleados)
   - Boletín de precios

3. **🧾 Servicio de Locales** (Próximamente)
   - Gestión de inventario
   - Ventas y empleados por local

4. **💳 Servicio de Pagos (Mock)** (Próximamente)
   - Simulación de pasarela de pago
   - Registro de transacciones

5. **🚗 Servicio de Parqueaderos (Mock)** (Próximamente)
   - Control de entradas/salidas
   - Reportes de ocupación

## 🏗️ Arquitectura

- **Backend**: Spring Boot + Java 21 + CockroachDB (compatibilidad PostgreSQL)
- **Frontend**: Angular 17 + TypeScript
- **Cloud**: Google Cloud Platform
  - Cloud Run (Backend)
  - Cloud Storage (Frontend)
  - Artifact Registry (Imágenes Docker)
- **CI/CD**: GitHub Actions

## ✅ Requisitos previos

Antes de iniciar el desarrollo, cada persona del equipo debe contar con:

- **Java 21** y **Maven Wrapper** (`./mvnw` ya incluido).
- **Node.js 18+** y **npm**.
- **Angular CLI 17+** (`npm install -g @angular/cli`).
- Acceso a internet para consumir CockroachDB y descargar dependencias.
- (Opcional) Cliente `cockroach` o cualquier cliente PostgreSQL para inspeccionar la base.

## 🌱 Preparar el entorno local

1. **Clonar el repositorio base**
   ```bash
   git clone <url-del-repo>
   cd kodehaus_app
   ```
2. **Crear una rama a partir de la base** (usa un nombre descriptivo)
   ```bash
   git checkout -b feature/<mi-feature>
   ```
3. **Configurar las variables de entorno** que usará Spring Boot. Se recomienda crear un archivo `.env` o exportarlas en la terminal antes de arrancar el backend.

   | Variable | Descripción |
   |----------|-------------|
   | `APP_ENV` | Entorno lógico (ej. `prod`, `test`, `local`). |
   | `SERVER_PORT` | Puerto HTTP del backend. |
   | `SPRING_DATASOURCE_URL` | URL JDBC hacia CockroachDB/PostgreSQL. |
   | `SPRING_DATASOURCE_USERNAME` | Usuario de la base. |
   | `SPRING_DATASOURCE_PASSWORD` | Contraseña de la base. |

> 💡 Puedes guardar estas variables en un archivo `stocks-backend/.env` y cargarlas con herramientas como [direnv](https://direnv.net/) o `source .env`.

## 🛠️ Desarrollo local

### Backend

1. Instala las dependencias (solo la primera vez):
   ```bash
   cd stocks-backend
   ./mvnw dependency:go-offline
   ```
2. Ejecuta la aplicación apuntando al entorno configurado:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Verifica el endpoint de salud (útil para despliegues y CI/CD):
   ```bash
   curl http://localhost:${SERVER_PORT:-8080}/health
   ```

El backend expone la API REST para gestionar stocks y un endpoint `/health` que valida conectividad con la base de datos.

### Frontend

1. Instala dependencias:
   ```bash
   cd stocks-frontend
   npm install
   ```
2. Ajusta el archivo `src/environments/environment.ts` si necesitas apuntar a otro backend (por defecto usa `http://localhost:8080`).
3. Levanta el servidor de desarrollo:
   ```bash
   npm start
   ```
4. Abre `http://localhost:4200` en tu navegador.

## � Funcionalidades Implementadas

### ✅ Servicio del Sistema

#### Gestión de Plazas
- CRUD completo de plazas de mercado
- Asignación de ubicaciones
- Gestión de módulos por plaza

#### Gestión de Gerentes ⭐ **NUEVO**
- Crear cuentas de gerentes
- Asignar gerentes a plazas (relación 1:1)
- Control de estados: ACTIVO, INACTIVO, SUSPENDIDO
- Validación de email e identificación únicos
- Endpoints para búsqueda y filtrado

Ver documentación detallada en:
- 📚 [API de Gerentes](./API_GERENTES.md)
- 📝 [Guía de Implementación](./IMPLEMENTACION_GERENTES.md)
- 🧪 [Colección Postman](./stocks-backend/postman_gerentes_collection.json)

#### Gestión de Módulos
- CRUD de módulos del sistema
- Habilitación/deshabilitación por plaza

#### Gestión de Suscripciones
- Creación de suscripciones
- Control de periodicidad y renovación
- Estados de pago

## �🔄 Flujo de trabajo recomendado

1. Asegúrate de tener la rama actualizada con `main` (`git pull origin main`).
2. Desarrolla tus cambios en tu rama feature y agrega tests si corresponde.
3. Haz commit de tus cambios y sube tu rama:
   ```bash
   git add .
   git commit -m "feat: describe tu cambio"
   git push origin feature/<mi-feature>
   ```
4. Crea un Pull Request en GitHub. El pipeline de CI/CD validará el `/health` y los builds.

## � Estructura del Proyecto

```
kodehaus_app/
├── stocks-backend/               # Backend Spring Boot
│   ├── src/main/java/
│   │   └── com/kodehaus/stocksbackend/
│   │       ├── model/           # Entidades JPA
│   │       │   ├── Plaza.java
│   │       │   ├── Gerente.java ⭐ NUEVO
│   │       │   ├── Modulo.java
│   │       │   ├── Plan.java
│   │       │   └── Suscripcion.java
│   │       ├── repository/      # Repositorios
│   │       ├── service/         # Lógica de negocio
│   │       ├── controller/      # REST Controllers
│   │       ├── dto/            # Data Transfer Objects
│   │       └── utils/          # Mappers y utilidades
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── db/migration/       # Scripts SQL
│   └── postman_*.json          # Colecciones Postman
├── stocks-frontend/             # Frontend Angular
│   └── src/app/
│       ├── dashboard/
│       ├── suscripciones/
│       └── services/
├── API_GERENTES.md             # Documentación API Gerentes ⭐
└── IMPLEMENTACION_GERENTES.md  # Guía de implementación ⭐
```

## �🚀 Deployment

Cuando los cambios se fusionan en `main`:

- `stocks-backend/` se despliega automáticamente a Cloud Run.
- `stocks-frontend/` se publica en Cloud Storage.

Cada despliegue usa el endpoint `/health` para validar la aplicación antes de exponerla.

## � Testing

### Backend
```bash
cd stocks-backend
./mvnw test
```

### Postman Collections
- `postman_collection.json` - Endpoints generales
- `postman_gerentes_collection.json` - Endpoints de gerentes ⭐

### Base de Datos
Ejecutar scripts en orden:
1. Crear tablas base (Plaza, Ubicacion, etc.)
2. `create_table_gerente.sql` ⭐ - Tabla de gerentes

## �🤝 Soporte y buenas prácticas

- Mantén las credenciales seguras; no las expongas en issues ni commits.
- Usa el sandbox `test` para experimentar sin afectar producción.
- Documenta en el PR cualquier configuración adicional que requiera el equipo.
- Revisa la documentación específica de cada módulo antes de implementar cambios.

## 🎯 Próximos Pasos

- [ ] Implementar autenticación JWT para gerentes
- [ ] Encriptación de contraseñas con BCrypt
- [ ] Servicio de Plaza (segundo microservicio)
- [ ] Servicio de Locales
- [ ] Servicio de Pagos (Mock)
- [ ] Servicio de Parqueaderos (Mock)
- [ ] Dashboard para gerentes en el frontend

---

**Branch actual:** `feature/suscripciones`  
**Última actualización:** 26 de Octubre de 2025
