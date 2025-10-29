# Implementación de Gestión de Gerentes - PlazApp

## 🎯 Objetivo
Implementar la funcionalidad para crear y gestionar las cuentas de los gerentes de plazas dentro del **Servicio del Sistema (Dueño del software)** de PlazApp.

## ✅ Componentes Implementados

### 1. Modelo de Datos
**Archivo:** `Gerente.java`
- Entidad JPA que representa a un gerente
- Relación 1:1 con Plaza
- Estados: ACTIVO, INACTIVO, SUSPENDIDO
- Campos de auditoría (fechas de creación y actualización)
- Validaciones de unicidad para email e identificación

### 2. Capa de Persistencia
**Archivo:** `GerenteRepository.java`
- Repository con métodos personalizados:
  - Búsqueda por email
  - Búsqueda por identificación
  - Búsqueda por estado
  - Búsqueda por plaza
  - Validaciones de existencia

### 3. DTOs (Data Transfer Objects)
- **CreateGerenteReq.java**: Datos para crear un gerente
- **UpdateGerenteReq.java**: Datos para actualizar un gerente
- **GerenteDTO.java**: Objeto de respuesta con información del gerente

### 4. Capa de Servicio
**Archivos:** `GerenteService.java` y `GerenteServiceImpl.java`

Operaciones implementadas:
- ✅ Crear gerente (con o sin plaza asignada)
- ✅ Actualizar gerente
- ✅ Obtener gerente por ID
- ✅ Obtener gerente por email
- ✅ Obtener gerente por plaza
- ✅ Listar todos los gerentes
- ✅ Filtrar gerentes por estado
- ✅ Asignar plaza a gerente
- ✅ Cambiar estado del gerente
- ✅ Eliminar gerente

**Validaciones:**
- Email único en el sistema
- Identificación única en el sistema
- Una plaza solo puede tener un gerente
- Validación de existencia de plaza al asignar

### 5. Controlador REST
**Archivo:** `GerenteController.java`

**Endpoints implementados:**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/gerentes` | Listar todos los gerentes |
| GET | `/api/gerentes/{id}` | Obtener gerente por ID |
| GET | `/api/gerentes/email/{email}` | Obtener gerente por email |
| GET | `/api/gerentes/plaza/{idPlaza}` | Obtener gerente de una plaza |
| GET | `/api/gerentes/estado/{estado}` | Filtrar por estado |
| POST | `/api/gerentes` | Crear nuevo gerente |
| PUT | `/api/gerentes/{id}` | Actualizar gerente |
| PUT | `/api/gerentes/{id}/asignar-plaza/{idPlaza}` | Asignar plaza |
| PUT | `/api/gerentes/{id}/estado` | Cambiar estado |
| DELETE | `/api/gerentes/{id}` | Eliminar gerente |

### 6. Mapper
**Archivo:** `GerenteMapper.java`
- Convierte entidades a DTOs
- Incluye información de la plaza asignada

### 7. Base de Datos
**Archivo:** `create_table_gerente.sql`
- Script SQL para crear la tabla `gerente`
- Definición de índices para optimización
- Constraints y foreign keys
- Validación de estados permitidos

### 8. Actualización del Modelo Plaza
**Archivo:** `Plaza.java`
- Se agregó la relación inversa `@OneToOne` con Gerente

## 📚 Documentación

### API Documentation
**Archivo:** `API_GERENTES.md`
- Descripción completa de todos los endpoints
- Ejemplos de request/response
- Modelo de datos
- Flujos de uso típicos
- Notas de seguridad
- Próximos pasos

### Colección de Postman
**Archivo:** `postman_gerentes_collection.json`
- Colección completa de endpoints listos para probar
- Ejemplos de todos los casos de uso
- Puede importarse directamente en Postman

## 🔄 Flujos de Negocio Implementados

### Flujo 1: Crear Plaza con Gerente
```
1. POST /api/plazas (crear plaza)
2. POST /api/gerentes (crear gerente con idPlaza)
3. El gerente queda asignado a su plaza
```

### Flujo 2: Asignar Gerente Posteriormente
```
1. POST /api/gerentes (crear gerente sin plaza)
2. PUT /api/gerentes/{id}/asignar-plaza/{idPlaza}
3. El gerente queda asignado
```

### Flujo 3: Suspender Gerente
```
1. PUT /api/gerentes/{id}/estado (estado: SUSPENDIDO)
2. El gerente no puede acceder al sistema
```

## 🔐 Consideraciones de Seguridad

⚠️ **Pendientes para Producción:**
1. **Encriptación de contraseñas**: Implementar BCrypt
2. **Autenticación**: Agregar JWT
3. **Autorización**: Validar permisos por rol
4. **Ocultar password**: No retornar en respuestas
5. **Rate limiting**: Prevenir ataques de fuerza bruta
6. **Validaciones**: Agregar validaciones @Valid en DTOs

## 📊 Modelo de Datos

```
┌─────────────────┐         ┌─────────────────┐
│     Gerente     │ 1    1  │      Plaza      │
├─────────────────┤◄────────┤─────────────────┤
│ id              │         │ id              │
│ nombre          │         │ nombre          │
│ apellido        │         │ contacto        │
│ email (unique)  │         │ dominio         │
│ password        │         │ fecha_creacion  │
│ telefono        │         │ id_ubicacion    │
│ identificacion  │         └─────────────────┘
│ estado          │
│ fecha_creacion  │
│ id_plaza (FK)   │
└─────────────────┘
```



## 📝 Notas de Implementación

1. **Estados de Gerente:**
   - `ACTIVO`: Puede acceder y administrar su plaza
   - `INACTIVO`: Cuenta deshabilitada temporalmente
   - `SUSPENDIDO`: Cuenta suspendida por incumplimiento

2. **Relación Plaza-Gerente:**
   - Es bidireccional (OneToOne)
   - Una plaza solo puede tener un gerente
   - Un gerente solo puede administrar una plaza
   - La asignación puede ser nula (gerente sin plaza)

3. **Validaciones Críticas:**
   - Email y identificación deben ser únicos
   - No se permiten duplicados en plazas
   - Los cambios de estado son auditados

## 🧪 Testing

Para probar la implementación:

1. **Importar colección en Postman**
   - Archivo: `postman_gerentes_collection.json`

2. **Crear base de datos**
   - Ejecutar: `create_table_gerente.sql`

3. **Iniciar aplicación**
   ```bash
   cd stocks-backend
   ./mvnw spring-boot:run
   ```

4. **Probar endpoints**
   - Usar Postman o curl
   - Verificar respuestas y validaciones

## 📞 Contacto y Soporte

Para dudas o mejoras, contactar al equipo de desarrollo de KodeHaus.

---

**Versión:** 1.0.0  
**Fecha:** 26 de Octubre de 2025  
**Branch:** feature/suscripciones
