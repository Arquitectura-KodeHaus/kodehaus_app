# Implementación de Registro de Usuario

## Descripción General
Se agregó la funcionalidad de registro para permitir a nuevos usuarios crear sus cuentas de gerente.

## Archivos Creados

### Frontend

1. **register/register.component.ts**
   - Componente standalone de Angular 17
   - Formulario reactivo con validación
   - Campos: nombre, apellido, email, password, teléfono, identificación
   - Validación de password mínimo 6 caracteres
   - Mensajes de éxito y error
   - Eventos personalizados para navegación

2. **register/register.component.html**
   - Formulario con diseño de dos columnas (nombre/apellido)
   - Campos requeridos con placeholders
   - Botones de registro y volver al login
   - Estados de loading, error y éxito

3. **register/register.component.css**
   - Tema verde (#2f855a) consistente con login
   - Header con gradiente
   - Diseño responsive (breakpoint 600px)
   - Animaciones de fade-in
   - Estados de validación de formulario

## Archivos Modificados

1. **login/login.component.html**
   - Agregado enlace "¿No tienes cuenta? Regístrate aquí"
   - CSS para .register-link

2. **login/login.component.ts**
   - Método `irARegistro()` que emite evento 'irARegistro'

3. **login/login.component.css**
   - Estilos para .register-link
   - Hover effects en enlace de registro

4. **app.component.ts**
   - Import de RegisterComponent
   - Variable `showRegister: boolean`
   - Event listeners para 'irARegistro', 'volverAlLogin', 'registroExitoso'

5. **app.component.html**
   - Renderizado condicional de LoginComponent y RegisterComponent
   - Lógica: mostrar registro si `showRegister === true`

## Flujo de Navegación

```
Login → Click "Regístrate aquí" → Registro
Registro → Click "Volver al Login" → Login
Registro → Registro exitoso (2 segundos) → Login
```

## API Utilizada

**Endpoint:** `POST http://localhost:2325/api/gerentes`

**Request Body:**
```json
{
  "nombre": "string",
  "apellido": "string",
  "email": "string",
  "password": "string",
  "telefono": "string",
  "identificacion": "string",
  "activo": true,
  "plazaId": null
}
```

**Response:** `GerenteDTO`

## Eventos Personalizados

- **irARegistro**: Emitido desde LoginComponent para mostrar formulario de registro
- **volverAlLogin**: Emitido desde RegisterComponent al hacer click en "Volver al Login"
- **registroExitoso**: Emitido desde RegisterComponent después de registro exitoso

## Validaciones

- Todos los campos son requeridos
- Password mínimo 6 caracteres
- Email debe tener formato válido (validación HTML5)
- Prevención de envíos duplicados durante el loading

## Seguridad

⚠️ **NOTA DE DESARROLLO**: Actualmente el password se envía sin encriptar. Para producción se debe:
- Implementar BCrypt en el backend
- Agregar hash antes de guardar en la base de datos
- Considerar implementar JWT tokens

## Testing

Para probar el registro:

1. Iniciar backend y frontend
2. Ir a http://localhost:4200
3. Click en "Regístrate aquí"
4. Llenar formulario con datos válidos
5. Click en "Registrarse"
6. Verificar mensaje de éxito
7. Automáticamente vuelve al login después de 2 segundos
8. Probar login con las credenciales registradas

## Paleta de Colores

- Verde principal: #2f855a
- Verde oscuro: #276749
- Gradiente header: linear-gradient(135deg, #2f855a, #276749)
- Texto: #2d3748
- Bordes: #e2e8f0
- Error: #e53e3e
- Éxito: #38a169

## Próximas Mejoras

1. Agregar confirmación de password
2. Validación de email único en tiempo real
3. Strength meter para password
4. Encriptación BCrypt
5. Captcha para prevenir bots
6. Verificación por email
