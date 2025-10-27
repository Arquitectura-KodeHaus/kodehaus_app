
##  Comandos para Correr PlazApp

## 1️⃣ Iniciar la Base de Datos (CockroachDB)

```powershell
# Iniciar el contenedor de CockroachDB
docker start crdb1

# Verificar que esté corriendo
docker ps
```

---

## 2️⃣ Iniciar el Backend (Spring Boot)

```powershell
# Navegar a la carpeta del backend
cd c:\Users\usuario\Documents\GitHub\kodehaus_app\stocks-backend

# Ejecutar el backend
.\mvnw.cmd spring-boot:run
```

✅ El backend estará disponible en: [http://localhost:2325](http://localhost:2325)

---

## 3️⃣ Iniciar el Frontend (Angular)

**En una nueva terminal PowerShell:**

```powershell
# Navegar a la carpeta del frontend
cd c:\Users\usuario\Documents\GitHub\kodehaus_app\stocks-frontend

# Instalar dependencias (solo la primera vez)
npm install

# Ejecutar el frontend
npm start
```

✅ El frontend estará disponible en: [http://localhost:4200](http://localhost:4200)

---

## 📝 Orden Recomendado de Inicio

1. **Base de datos** (CockroachDB) - debe estar corriendo primero
2. **Backend** (Spring Boot) - espera a ver el mensaje `Started StocksBackendApplication`
3. **Frontend** (Angular) - espera a ver `Compiled successfully`

---

## 🔍 Verificación Rápida

```powershell
# Verificar que CockroachDB esté corriendo
docker ps | findstr crdb1

# Verificar que el backend esté corriendo
netstat -an | findstr "2325"

# Verificar que el frontend esté corriendo
netstat -an | findstr "4200"
```

---