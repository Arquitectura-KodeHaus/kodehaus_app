# 🏢 Kodehaus App - Stock Platform

Plataforma de gestión de stocks construida con **Spring Boot** (backend) y **Angular** (frontend), desplegada en **Google Cloud Platform**.

## 🏗️ Arquitectura

- **Backend**: Spring Boot + Java 21 + CockroachDB
- **Frontend**: Angular 17 + TypeScript
- **Cloud**: Google Cloud Platform
  - Cloud Run (Backend)
  - Cloud Storage (Frontend)
  - Artifact Registry (Docker images)
- **CI/CD**: GitHub Actions

## 🚀 Deployment

El deployment es automático mediante GitHub Actions:
- Push a `main` en `stocks-backend/` → Deploy a Cloud Run
- Push a `main` en `stocks-frontend/` → Deploy a Cloud Storage

## 🔗 URLs

- **Backend API**: https://stocks-backend-service-XXXXX-uc.a.run.app
- **Frontend**: https://storage.googleapis.com/stocks-frontend-bucket/index.html

## 🛠️ Desarrollo Local

### Backend
```bash
cd stocks-backend
./mvnw spring-boot:run