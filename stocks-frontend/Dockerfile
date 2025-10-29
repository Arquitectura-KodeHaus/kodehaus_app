# syntax=docker/dockerfile:1

# ==========================================
# Stage 1: Build de Angular
# ==========================================
FROM node:20-alpine AS build

# Argumento para la URL del backend (se pasa en build time)
ARG BACKEND_URL
ENV BACKEND_URL=${BACKEND_URL}

WORKDIR /app

# Copiar package files y instalar dependencias
COPY package*.json ./
RUN npm ci --silent

# Copiar código fuente
COPY . .

# Reemplazar la variable ${API_URL} en environment.prod.ts
RUN sed -i "s|\${API_URL}|${BACKEND_URL}|g" src/environments/environment.prod.ts

# Verificar el reemplazo
RUN echo "=== Verificando environment.prod.ts ===" && \
    cat src/environments/environment.prod.ts

# Build de producción
RUN npm run build -- --configuration production

# Verificar output
RUN ls -la dist/stocks-frontend/

# ==========================================
# Stage 2: Runtime con Nginx
# ==========================================
FROM nginx:1.25-alpine

# Metadata
LABEL maintainer="kodehaus-team"
LABEL app="stocks-frontend"

# Copiar build de Angular
COPY --from=build /app/dist/stocks-frontend /usr/share/nginx/html

# Configuración de Nginx para SPA
RUN cat > /etc/nginx/conf.d/default.conf << 'EOF'
server {
    listen 8080;
    server_name _;
    root /usr/share/nginx/html;
    index index.html;

    # Logging
    access_log /var/log/nginx/access.log;
    error_log /var/log/nginx/error.log warn;

    # Gzip compression
    gzip on;
    gzip_vary on;
    gzip_types text/plain text/css text/xml text/javascript 
               application/x-javascript application/xml+rss 
               application/javascript application/json;
    gzip_min_length 1000;
    gzip_proxied any;
    gzip_comp_level 6;

    # Security headers
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;

    # Cache estático
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg|woff|woff2|ttf|eot)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    # No cache para index.html
    location = /index.html {
        add_header Cache-Control "no-cache, no-store, must-revalidate";
        expires 0;
    }

    # SPA fallback - todas las rutas van a index.html
    location / {
        try_files $uri $uri/ /index.html;
    }

    # Health check
    location /health {
        access_log off;
        return 200 "healthy\n";
        add_header Content-Type text/plain;
    }
}
EOF

# Nginx en modo no-daemon y usuario no-root
RUN chown -R nginx:nginx /usr/share/nginx/html && \
    chown -R nginx:nginx /var/cache/nginx && \
    chown -R nginx:nginx /var/log/nginx && \
    touch /var/run/nginx.pid && \
    chown -R nginx:nginx /var/run/nginx.pid

# Cambiar a usuario no-root
USER nginx

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/health || exit 1

# Comando de inicio
CMD ["nginx", "-g", "daemon off;"]
