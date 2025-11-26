# 📊 Dónde se Guardan los Usuarios Registrados

## 🗄️ Base de Datos

Los usuarios (gerentes) se guardan en la base de datos **CockroachDB** que está corriendo en tu máquina local.

### Configuración de la Base de Datos

```properties
# Configuración en: stocks-backend/src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:26257/stocksdb?sslmode=disable
spring.datasource.username=root
spring.datasource.password=
```

### Tabla de Gerentes

Los usuarios se almacenan en la tabla `gerente` con la siguiente estructura:

```sql
CREATE TABLE gerente (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR NOT NULL,
    apellido VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    telefono VARCHAR NOT NULL,
    identificacion VARCHAR NOT NULL,
    estado VARCHAR NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_ultima_actualizacion TIMESTAMP,
    id_plaza BIGINT UNIQUE
);
```

---

## 🔍 Cómo Ver los Usuarios Registrados

### Opción 1: Usando la API REST

**Listar todos los gerentes:**
```powershell
Invoke-RestMethod -Uri http://localhost:2325/api/gerentes -Method Get
```

**Ver un gerente específico por email:**
```powershell
Invoke-RestMethod -Uri "http://localhost:2325/api/gerentes/email/test@test.com" -Method Get
```

**Ver un gerente específico por ID:**
```powershell
Invoke-RestMethod -Uri "http://localhost:2325/api/gerentes/1126962157133758465" -Method Get
```

### Opción 2: Desde el Frontend

1. Accede a http://localhost:4200
2. Inicia sesión con una cuenta de gerente
3. Ve a la sección **"Gerentes"** en el menú lateral
4. Verás la lista completa de gerentes registrados

### Opción 3: Usando la Consola de CockroachDB

```powershell
# Conectarse a CockroachDB
cockroach sql --insecure --host=localhost:26257

# Una vez conectado, ejecutar:
USE stocksdb;
SELECT * FROM gerente;
```

### Opción 4: Usando pgAdmin o DBeaver

Puedes usar cualquier cliente de PostgreSQL para conectarte a CockroachDB:

**Parámetros de conexión:**
- Host: `localhost`
- Puerto: `26257`
- Database: `stocksdb`
- Username: `root`
- Password: (vacío)

---

## 👥 Usuarios Actuales en el Sistema

Actualmente tienes estos usuarios registrados:

| Email | Password | Nombre |
|-------|----------|--------|
| ji@gmail.com | aa | juan jimenez |
| mm@gmail.com | aa | mm kk |
| test@test.com | 123456 | Test Usuario |
| nuevo@test.com | pass123 | Nuevo Gerente |

---

## 🔐 Seguridad

⚠️ **IMPORTANTE**: Actualmente las contraseñas se almacenan en **texto plano** (sin encriptar).

**Esto es solo para desarrollo.** Para producción, deberías:
1. Usar BCrypt para encriptar las contraseñas
2. Implementar Spring Security
3. Usar JWT tokens para autenticación

---

## 📝 Comandos Útiles

### Crear un nuevo usuario desde PowerShell:
```powershell
$body = @{
    nombre = "Juan"
    apellido = "Pérez"
    email = "juan@ejemplo.com"
    password = "mipassword"
    telefono = "1234567890"
    identificacion = "12345678"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:2325/api/gerentes -Method Post -ContentType 'application/json' -Body $body
```

### Verificar login:
```powershell
$body = '{"email":"test@test.com","password":"123456"}'
Invoke-RestMethod -Uri http://localhost:2325/api/auth/login -Method Post -ContentType 'application/json' -Body $body
```

### Eliminar un usuario:
```powershell
Invoke-RestMethod -Uri "http://localhost:2325/api/gerentes/ID_DEL_USUARIO" -Method Delete
```

---

## 🚀 Iniciar el Sistema

Para ver los usuarios necesitas tener ambos servicios corriendo:

### Backend:
```powershell
cd stocks-backend
Start-Process java -ArgumentList '-jar','target\stocks-backend-1.0.0.jar' -WindowStyle Hidden
```

### Frontend:
```powershell
cd stocks-frontend
npm start
```

Luego accede a http://localhost:4200 y inicia sesión para ver la gestión de gerentes.
