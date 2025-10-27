# API de Gestión de Gerentes - PlazApp

## Descripción
API para gestionar las cuentas de gerentes de plazas en el sistema PlazApp (Servicio del Sistema - Dueño del software).

## Endpoints

### 1. Crear un Gerente

**POST** `/api/gerentes`

Crea una nueva cuenta de gerente. Opcionalmente puede asignar una plaza al momento de la creación.

**Request Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@plazapp.com",
  "password": "Password123!",
  "telefono": "+57 300 123 4567",
  "identificacion": "1234567890",
  "idPlaza": 1
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@plazapp.com",
  "telefono": "+57 300 123 4567",
  "identificacion": "1234567890",
  "estado": "ACTIVO",
  "fechaCreacion": "2025-10-26T10:30:00",
  "fechaUltimaActualizacion": null,
  "idPlaza": 1,
  "nombrePlaza": "Corabastos"
}
```

**Validaciones:**
- Email debe ser único
- Identificación debe ser única
- Una plaza solo puede tener un gerente asignado
- Estado por defecto: ACTIVO

---

### 2. Obtener Todos los Gerentes

**GET** `/api/gerentes`

Retorna la lista completa de gerentes registrados.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@plazapp.com",
    "telefono": "+57 300 123 4567",
    "identificacion": "1234567890",
    "estado": "ACTIVO",
    "fechaCreacion": "2025-10-26T10:30:00",
    "fechaUltimaActualizacion": null,
    "idPlaza": 1,
    "nombrePlaza": "Corabastos"
  },
  {
    "id": 2,
    "nombre": "María",
    "apellido": "García",
    "email": "maria.garcia@plazapp.com",
    "telefono": "+57 310 987 6543",
    "identificacion": "9876543210",
    "estado": "ACTIVO",
    "fechaCreacion": "2025-10-26T11:15:00",
    "fechaUltimaActualizacion": null,
    "idPlaza": null,
    "nombrePlaza": null
  }
]
```

---

### 3. Obtener Gerente por ID

**GET** `/api/gerentes/{id}`

**Response (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@plazapp.com",
  "telefono": "+57 300 123 4567",
  "identificacion": "1234567890",
  "estado": "ACTIVO",
  "fechaCreacion": "2025-10-26T10:30:00",
  "fechaUltimaActualizacion": null,
  "idPlaza": 1,
  "nombrePlaza": "Corabastos"
}
```

**Response (404 Not Found):** Si el gerente no existe

---

### 4. Obtener Gerente por Email

**GET** `/api/gerentes/email/{email}`

Ejemplo: `/api/gerentes/email/juan.perez@plazapp.com`

**Response:** Similar al endpoint por ID

---

### 5. Obtener Gerente por Plaza

**GET** `/api/gerentes/plaza/{idPlaza}`

Retorna el gerente asignado a una plaza específica.

Ejemplo: `/api/gerentes/plaza/1`

**Response (200 OK):** Datos del gerente
**Response (404 Not Found):** Si la plaza no tiene gerente asignado

---

### 6. Obtener Gerentes por Estado

**GET** `/api/gerentes/estado/{estado}`

Estados válidos: `ACTIVO`, `INACTIVO`, `SUSPENDIDO`

Ejemplo: `/api/gerentes/estado/ACTIVO`

**Response (200 OK):** Array de gerentes con el estado especificado

---

### 7. Actualizar Gerente

**PUT** `/api/gerentes/{id}`

Actualiza los datos de un gerente. Todos los campos son opcionales.

**Request Body:**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "telefono": "+57 300 999 8888",
  "estado": "ACTIVO",
  "idPlaza": 2
}
```

**Response (200 OK):** Datos actualizados del gerente

**Validaciones:**
- Si se cambia la plaza, verifica que no tenga otro gerente asignado
- El email y la identificación no se pueden modificar (crear nuevo gerente si es necesario)

---

### 8. Asignar Plaza a Gerente

**PUT** `/api/gerentes/{idGerente}/asignar-plaza/{idPlaza}`

Asigna una plaza a un gerente existente.

Ejemplo: `/api/gerentes/1/asignar-plaza/3`

**Response (200 OK):**
```json
{
  "mensaje": "Plaza asignada exitosamente"
}
```

**Response (400 Bad Request):** Si la plaza ya tiene gerente asignado

---

### 9. Cambiar Estado de Gerente

**PUT** `/api/gerentes/{id}/estado`

Cambia el estado de un gerente.

**Request Body:**
```json
{
  "estado": "SUSPENDIDO"
}
```

**Response (200 OK):**
```json
{
  "mensaje": "Estado actualizado exitosamente"
}
```

Estados válidos: `ACTIVO`, `INACTIVO`, `SUSPENDIDO`

---

### 10. Eliminar Gerente

**DELETE** `/api/gerentes/{id}`

Elimina un gerente del sistema.

**Response (204 No Content):** Eliminación exitosa
**Response (404 Not Found):** Si el gerente no existe

---

## Flujo de Uso Típico

### Escenario 1: Crear Plaza con Gerente

1. Crear la plaza (POST `/api/plazas`)
2. Crear el gerente y asignar la plaza (POST `/api/gerentes` con `idPlaza`)
3. El gerente ahora puede administrar su plaza

### Escenario 2: Asignar Gerente a Plaza Existente

1. Crear el gerente sin plaza (POST `/api/gerentes` sin `idPlaza`)
2. Asignar plaza posteriormente (PUT `/api/gerentes/{id}/asignar-plaza/{idPlaza}`)

### Escenario 3: Gestión de Suscripciones (Próximamente)

1. El gerente selecciona un plan
2. El sistema crea la suscripción
3. El gerente realiza el pago
4. Se activan los módulos correspondientes

---

## Modelo de Datos

### Gerente

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | Identificador único |
| nombre | String | Nombre del gerente |
| apellido | String | Apellido del gerente |
| email | String | Email único (login) |
| password | String | Contraseña (encriptada en producción) |
| telefono | String | Teléfono de contacto |
| identificacion | String | Documento de identidad único |
| estado | String | ACTIVO, INACTIVO, SUSPENDIDO |
| fechaCreacion | LocalDateTime | Fecha de creación automática |
| fechaUltimaActualizacion | LocalDateTime | Fecha de última modificación |
| plaza | Plaza | Plaza asignada (relación 1:1) |

---

## Notas de Seguridad

⚠️ **IMPORTANTE:** 
- En producción, las contraseñas deben encriptarse usando BCrypt o similar
- Implementar autenticación JWT para proteger los endpoints
- Validar permisos según el rol del usuario
- El campo password NO debe retornarse en las respuestas (ajustar mapper)

---

## Próximos Pasos

- [ ] Implementar encriptación de passwords
- [ ] Agregar autenticación y autorización (JWT)
- [ ] Crear endpoint de login para gerentes
- [ ] Integrar con servicio de notificaciones (email)
- [ ] Dashboard para gerentes
- [ ] Gestión de módulos y permisos por gerente
