# Frontend - Gestión de Gerentes

## 📦 Archivos Creados

### Modelos
- ✅ `src/app/models/gerente.ts` - Interfaces TypeScript para Gerente

### Servicios
- ✅ `src/app/services/gerentes.service.ts` - Service para consumir API de gerentes

### Componentes
- ✅ `src/app/gerentes/gerentes.component.ts` - Lógica del componente
- ✅ `src/app/gerentes/gerentes.component.html` - Template HTML
- ✅ `src/app/gerentes/gerentes.component.css` - Estilos del componente

### Actualizado
- ✅ `src/app/app.component.ts` - Importación del componente
- ✅ `src/app/app.component.html` - Navegación en sidebar

## 🎨 Funcionalidades Implementadas

### 1. Listado de Gerentes
- Tabla responsive con todos los gerentes
- Información completa: nombre, email, teléfono, identificación, plaza, estado
- Estados visuales con badges de colores

### 2. Búsqueda y Filtros
- **Búsqueda en tiempo real** por:
  - Nombre
  - Apellido
  - Email
  - Identificación
  - Nombre de plaza
- **Filtro por estado:**
  - Todos
  - ACTIVO
  - INACTIVO
  - SUSPENDIDO

### 3. Crear Gerente
- Modal con formulario completo
- Validación de campos requeridos
- Asignación opcional de plaza al momento de crear
- Campos:
  - Nombre y apellido
  - Email (único)
  - Contraseña
  - Teléfono
  - Identificación (única)
  - ID Plaza (opcional)

### 4. Editar Gerente
- Modal con datos prellenados
- Campos editables: nombre, apellido, teléfono, plaza
- Campos bloqueados: email, identificación (no se pueden cambiar)
- Password no se muestra ni edita por seguridad

### 5. Ver Detalle
- Modal con información completa del gerente
- Muestra todos los datos incluyendo:
  - Fechas de creación y actualización
  - Estado actual
  - Plaza asignada

### 6. Cambiar Estado
- Selector dropdown en la tabla
- Cambio inmediato con confirmación
- Estados: ACTIVO, INACTIVO, SUSPENDIDO

### 7. Eliminar Gerente
- Botón de eliminación con confirmación
- Feedback inmediato al usuario

## 🎨 Diseño UI/UX

### Colores de Estados
```css
ACTIVO      → Verde (#c6f6d5, #22543d)
INACTIVO    → Rojo   (#fed7d7, #742a2a)
SUSPENDIDO  → Naranja (#feebc8, #7c2d12)
```

### Características
- ✅ Diseño moderno y limpio
- ✅ Responsive (móvil y desktop)
- ✅ Modales accesibles
- ✅ Feedback visual en hover
- ✅ Loading states
- ✅ Error handling
- ✅ Confirmaciones para acciones destructivas

## 🔌 Integración con Backend

El servicio consume los siguientes endpoints:

```typescript
GET    /api/gerentes                          → list()
GET    /api/gerentes/{id}                     → getById(id)
GET    /api/gerentes/email/{email}            → getByEmail(email)
GET    /api/gerentes/plaza/{idPlaza}          → getByPlaza(idPlaza)
GET    /api/gerentes/estado/{estado}          → getByEstado(estado)
POST   /api/gerentes                          → create(gerente)
PUT    /api/gerentes/{id}                     → update(id, gerente)
PUT    /api/gerentes/{id}/asignar-plaza/{id}  → asignarPlaza(idGerente, idPlaza)
PUT    /api/gerentes/{id}/estado              → cambiarEstado(id, estado)
DELETE /api/gerentes/{id}                     → delete(id)
```

## 🚀 Cómo Probar

### 1. Asegúrate de que el backend esté corriendo
```bash
cd stocks-backend
./mvnw spring-boot:run
```

### 2. Instala dependencias del frontend (si no lo has hecho)
```bash
cd stocks-frontend
npm install
```

### 3. Inicia el servidor de desarrollo
```bash
npm start
```

### 4. Abre el navegador
- URL: `http://localhost:4200`
- Click en "Gerentes" en el menú lateral
- ¡Empieza a usar la interfaz!

## 📋 Flujo de Uso

### Crear un Gerente
1. Click en "Nuevo Gerente"
2. Llenar formulario
3. (Opcional) Asignar ID de plaza
4. Click en "Crear"
5. ¡Listo! El gerente aparece en la lista

### Editar un Gerente
1. Click en el icono de editar (✏️)
2. Modificar campos deseados
3. Click en "Actualizar"
4. Cambios reflejados inmediatamente

### Cambiar Estado
1. Usar el selector en la columna de acciones
2. Seleccionar nuevo estado
3. Confirmar cambio
4. Estado actualizado

### Ver Detalle
1. Click en el icono de ojo (👁️)
2. Ver toda la información
3. Click en "Cerrar"

### Buscar/Filtrar
1. Usar el campo de búsqueda para texto libre
2. Usar el selector de estado para filtrar por estado
3. Los resultados se actualizan en tiempo real

## 🔧 Configuración

### Variables de Entorno

Asegúrate de que `environment.ts` tenga la URL correcta del backend:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

## ⚠️ Notas Importantes

1. **Validación de Email**: El backend valida que el email sea único. Si intentas crear un gerente con un email existente, verás un mensaje de error.

2. **Validación de Identificación**: Similar al email, la identificación debe ser única.

3. **Plaza Única**: Una plaza solo puede tener un gerente asignado. El backend validará esto.

4. **Password**: En la edición, el password NO se muestra ni se puede cambiar desde este formulario (por seguridad).

5. **ID Plaza**: Actualmente se ingresa manualmente. En una versión futura se podría implementar un selector dropdown con la lista de plazas disponibles.

## 🎯 Mejoras Futuras

- [ ] Selector de plazas (dropdown en lugar de input numérico)
- [ ] Paginación de tabla
- [ ] Exportar datos a CSV/Excel
- [ ] Validaciones de formulario más robustas
- [ ] Toast notifications en lugar de alerts
- [ ] Confirmación visual de operaciones exitosas
- [ ] Filtros avanzados (por fecha de creación, por plaza, etc.)
- [ ] Vista de cards alternativa a la tabla
- [ ] Ordenamiento de columnas
- [ ] Cambio de contraseña desde la interfaz

## 📱 Responsive Design

El componente es completamente responsive:

- **Desktop**: Tabla completa con todos los campos
- **Tablet**: Tabla ajustada con scrolling horizontal si es necesario
- **Mobile**: Tabla compacta con acciones apiladas

## 🎨 Capturas de Pantalla

La interfaz incluye:
- Header con título y botón de crear
- Barra de búsqueda y filtros
- Tabla con estados visuales
- Modales centrados y accesibles
- Botones de acción intuitivos

---

**¡El módulo de Gerentes está listo para usar!** 🎉
