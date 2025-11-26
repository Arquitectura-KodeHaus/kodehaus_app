# 🔐 Sistema de Login - PlazApp

## ✅ Login Implementado

Se ha implementado un sistema de login básico con autenticación por email y contraseña.

### 🎨 Características

- ✅ Pantalla de login con diseño profesional usando colores de PlazApp (verde)
- ✅ Validación de credenciales contra la tabla de gerentes
- ✅ Verificación de estado del gerente (solo ACTIVO puede iniciar sesión)
- ✅ Sesión guardada en localStorage
- ✅ Información del usuario en el sidebar
- ✅ Botón de cerrar sesión
- ✅ Protección de rutas (solo usuarios autenticados ven la aplicación)

---

## 🚀 Cómo Probar el Login

### 1. Crear un Gerente de Prueba

**Opción A: Usando Postman o la API**
```bash
POST http://localhost:2325/api/gerentes
Content-Type: application/json

{
  "nombre": "Admin",
  "apellido": "Sistema",
  "email": "admin@plazapp.com",
  "password": "123456",
  "telefono": "+57 300 000 0000",
  "identificacion": "1000000000"
}
```

**Opción B: Usando SQL directo**
```powershell
docker exec -it crdb1 ./cockroach sql --insecure --database=stocksdb -e "INSERT INTO gerente (nombre, apellido, email, password, telefono, identificacion, estado, fecha_creacion, fecha_ultima_actualizacion) VALUES ('Admin', 'Sistema', 'admin@plazapp.com', '123456', '+57 300 000 0000', '1000000000', 'ACTIVO', NOW(), NOW());"
```

### 2. Iniciar la Aplicación

```powershell
# 1. Iniciar CockroachDB
docker start crdb1

# 2. Iniciar Backend (en terminal separada)
cd c:\Users\usuario\Documents\GitHub\kodehaus_app\stocks-backend
cmd /c run.bat

# 3. Iniciar Frontend (en terminal separada)
cd c:\Users\usuario\Documents\GitHub\kodehaus_app\stocks-frontend
npm start
```

### 3. Probar el Login

1. Abre **http://localhost:4200**
2. Verás la pantalla de login
3. Ingresa las credenciales:
   - **Email:** `admin@plazapp.com`
   - **Password:** `123456`
4. Haz clic en "Iniciar Sesión"
5. ¡Listo! Deberías ver el dashboard con tu nombre en el sidebar

---

## 🎯 Credenciales de Prueba

| Email | Password | Estado |
|-------|----------|--------|
| admin@plazapp.com | 123456 | ACTIVO |

---

## 📁 Archivos Creados

### Backend
- `AuthController.java` - Controlador de autenticación
- `LoginRequest.java` - DTO para request de login
- `LoginResponse.java` - DTO para response de login

### Frontend
- `auth.service.ts` - Servicio de autenticación
- `login.component.ts` - Componente de login
- `login.component.html` - Template del login
- `login.component.css` - Estilos del login

### Modificados
- `app.component.ts` - Integración con sistema de auth
- `app.component.html` - Mostrar login o app según estado
- `app.component.css` - Estilos mejorados del sidebar con info de usuario
- `GerenteDTO.java` - Agregado campo password
- `GerenteMapper.java` - Mapeo de password

---

## 🔒 Seguridad

⚠️ **NOTA IMPORTANTE:** Esta es una implementación básica para desarrollo.

**Para producción se debe:**
1. Encriptar passwords con BCrypt
2. Usar JWT o tokens de sesión
3. Implementar HTTPS
4. Agregar rate limiting
5. Implementar refresh tokens
6. No enviar passwords en las respuestas

---

## 🧪 Endpoint de Login

### POST `/api/auth/login`

**Request:**
```json
{
  "email": "admin@plazapp.com",
  "password": "123456"
}
```

**Response (éxito):**
```json
{
  "success": true,
  "message": "Login exitoso",
  "gerente": {
    "id": 1,
    "nombre": "Admin",
    "apellido": "Sistema",
    "email": "admin@plazapp.com",
    "telefono": "+57 300 000 0000",
    "identificacion": "1000000000",
    "estado": "ACTIVO",
    "password": null
  }
}
```

**Response (error):**
```json
{
  "success": false,
  "message": "Credenciales inválidas",
  "gerente": null
}
```

---

## 💡 Funcionalidades

### Login
- Validación de email y password
- Verificación de estado del gerente
- Mensaje de error claro
- Loading state durante la validación

### Sesión
- Se guarda en localStorage
- Persiste al recargar la página
- Se limpia al cerrar sesión

### UI/UX
- Diseño moderno y profesional
- Colores corporativos de PlazApp (verde)
- Animaciones suaves
- Responsive design
- Información del usuario en sidebar
- Avatar con iniciales
- Botón de logout

---

## 🎨 Paleta de Colores

```css
Verde Principal: #2f855a
Verde Oscuro: #276749
Gris Oscuro: #1f2937
Gris Más Oscuro: #111827
Rojo Error: #e53e3e
```

---

## 📝 Próximos Pasos

- [ ] Implementar encriptación de passwords (BCrypt)
- [ ] Agregar JWT para tokens de sesión
- [ ] Implementar recuperación de contraseña
- [ ] Agregar autenticación de 2 factores
- [ ] Implementar roles y permisos
- [ ] Agregar logs de auditoría de accesos

---

**Versión:** 1.0.0  
**Fecha:** 24 de Noviembre de 2025  
**Branch:** Gerentes
