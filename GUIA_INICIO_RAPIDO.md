# 🚀 Guía Rápida de Inicio - Gestión de Gerentes

## ⚡ Inicio Rápido (5 minutos)

### Paso 1: Base de Datos (1 min)

Ejecuta el script SQL para crear la tabla de gerentes:

**Ubicación:** `stocks-backend/src/main/resources/db/migration/create_table_gerente.sql`

```sql
-- Copiar y ejecutar el contenido completo del archivo en tu base de datos
```

### Paso 2: Iniciar Backend (1 min)

```powershell
cd stocks-backend
./mvnw spring-boot:run
```

✅ Espera a ver: `Started StocksBackendApplication`  
✅ Backend en: `http://localhost:8080`

### Paso 3: Iniciar Frontend (2 min)

```powershell
cd stocks-frontend
npm install  # Solo la primera vez
npm start
```

✅ Frontend en: `http://localhost:4200`

### Paso 4: ¡Usar la Aplicación! (1 min)

1. Abre `http://localhost:4200`
2. Click en **"Gerentes"** en el menú lateral izquierdo
3. Click en **"+ Nuevo Gerente"**
4. Llena el formulario:
   ```
   Nombre: Juan
   Apellido: Pérez
   Email: juan.perez@plazapp.com
   Password: Password123!
   Teléfono: +57 300 123 4567
   Identificación: 1234567890
   ID Plaza: (dejar vacío o poner 1 si existe)
   ```
5. Click en **"Crear"**
6. ✨ ¡Tu primer gerente está creado!

---

## 🧪 Probar Todas las Funcionalidades

### ✅ 1. Crear Gerente
- [x] Click en "Nuevo Gerente"
- [x] Llenar formulario
- [x] Click en "Crear"
- [x] Ver gerente en la tabla

### ✅ 2. Buscar Gerente
- [x] Escribir en el campo de búsqueda
- [x] Ver resultados filtrados en tiempo real

### ✅ 3. Filtrar por Estado
- [x] Seleccionar "ACTIVO" en el filtro
- [x] Ver solo gerentes activos

### ✅ 4. Ver Detalle
- [x] Click en el icono 👁️
- [x] Ver información completa
- [x] Click en "Cerrar"

### ✅ 5. Editar Gerente
- [x] Click en el icono ✏️
- [x] Modificar nombre o teléfono
- [x] Click en "Actualizar"
- [x] Ver cambios reflejados

### ✅ 6. Cambiar Estado
- [x] Usar el selector de estado en la tabla
- [x] Cambiar a "SUSPENDIDO"
- [x] Confirmar cambio
- [x] Ver badge actualizado

### ✅ 7. Eliminar Gerente
- [x] Click en el icono 🗑️
- [x] Confirmar eliminación
- [x] Ver que desaparece de la tabla

---

## 🔧 Solución de Problemas

### ❌ Error: "Ya existe un gerente con el email..."

**Causa:** Email duplicado  
**Solución:** Usar un email diferente

### ❌ Error: "La plaza ya tiene un gerente asignado"

**Causa:** Intentas asignar una plaza que ya tiene gerente  
**Solución:** Verificar qué gerente tiene la plaza o usar otra plaza

### ❌ Error: "Plaza no encontrada"

**Causa:** El ID de plaza no existe  
**Solución:** Dejar el campo vacío o verificar IDs de plazas existentes

### ❌ Frontend no carga datos

**Causa:** Backend no está corriendo  
**Solución:** Verificar que `http://localhost:8080/api/gerentes` responda

### ❌ Error de CORS

**Causa:** Backend no acepta peticiones del frontend  
**Solución:** Verificar WebConfig en el backend

---

## 📸 Capturas de Pantalla Esperadas

### Vista Principal
```
┌─────────────────────────────────────────────────────────────┐
│ Gestión de Gerentes                           + Nuevo       │
├─────────────────────────────────────────────────────────────┤
│ [Búsqueda...] [Estado: Todos ▼]                            │
├──────┬─────────┬──────────┬──────────┬─────────┬───────────┤
│ Nombre│Email   │Teléfono  │ID        │Plaza    │Estado│Acc│
├──────┼─────────┼──────────┼──────────┼─────────┼──────┼───┤
│Juan P│juan@... │+57 300...│123456... │Central  │●ACTIVO│..│
└──────┴─────────┴──────────┴──────────┴─────────┴──────┴───┘
```

### Modal de Creación
```
┌────────────────────────────┐
│ Nuevo Gerente           ✕ │
├────────────────────────────┤
│ Nombre: [        ]         │
│ Apellido: [      ]         │
│ Email: [         ]         │
│ Password: [******]         │
│ Teléfono: [      ]         │
│ ID: [            ]         │
│ Plaza: [         ]         │
├────────────────────────────┤
│        [Cancelar] [Crear]  │
└────────────────────────────┘
```

---

## 🎯 Casos de Uso Comunes

### 🏢 Caso 1: Nueva Plaza con Gerente

```
1. Crear la plaza (si no existe)
2. Anotar el ID de la plaza
3. Crear gerente con ese ID de plaza
4. ¡Listo! El gerente administra su plaza
```

### 👤 Caso 2: Crear Gerente sin Plaza

```
1. Crear gerente dejando "ID Plaza" vacío
2. Más tarde, asignar plaza desde la tabla
3. O editar el gerente y agregar ID de plaza
```

### 🔄 Caso 3: Cambiar Gerente de Plaza

```
1. Buscar el gerente actual
2. Click en editar ✏️
3. Cambiar el ID de plaza
4. Click en "Actualizar"
5. La plaza ahora tiene nuevo gerente
```

### 🚫 Caso 4: Suspender Gerente

```
1. Buscar el gerente
2. En el selector de estado, elegir "SUSPENDIDO"
3. Confirmar cambio
4. El gerente ya no puede acceder al sistema
```

---

## 📋 Checklist de Validación

Antes de dar por terminado, verifica:

### Backend
- [ ] Backend corre sin errores
- [ ] Endpoint `/api/gerentes` responde
- [ ] Tabla `gerente` existe en la base de datos
- [ ] POST crea gerentes correctamente
- [ ] GET lista gerentes
- [ ] PUT actualiza gerentes
- [ ] DELETE elimina gerentes

### Frontend
- [ ] Frontend carga correctamente
- [ ] Menú "Gerentes" está visible
- [ ] Click en "Gerentes" muestra el componente
- [ ] Botón "Nuevo Gerente" funciona
- [ ] Modal de creación se abre
- [ ] Formulario valida campos
- [ ] Tabla muestra datos del backend
- [ ] Búsqueda filtra en tiempo real
- [ ] Filtros funcionan
- [ ] Edición funciona
- [ ] Eliminación funciona
- [ ] Estados se pueden cambiar

---

## 📞 ¿Necesitas Ayuda?

### Revisar Logs

**Backend:**
```powershell
# Ver logs en la consola donde corre el backend
# Buscar errores que contengan "Gerente"
```

**Frontend:**
```powershell
# Abrir DevTools en el navegador (F12)
# Ir a Console
# Buscar errores en rojo
```

### Verificar API

```powershell
# Probar endpoint directamente
curl http://localhost:8080/api/gerentes
```

### Revisar Base de Datos

```sql
-- Ver todos los gerentes
SELECT * FROM gerente;

-- Ver estructura de la tabla
DESCRIBE gerente;
```

---

## 🎉 ¡Todo Listo!

Si completaste todos los pasos, ahora tienes:

✅ Sistema completo de gestión de gerentes  
✅ Backend funcionando con 10 endpoints  
✅ Frontend con interfaz moderna  
✅ Base de datos configurada  
✅ Documentación completa  

**¡Felicitaciones! El módulo está 100% operativo.** 🚀

---

**Tip Pro:** Importa `postman_gerentes_collection.json` en Postman para probar todos los endpoints de forma rápida.
