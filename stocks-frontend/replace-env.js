// replace-env.js
// Script para reemplazar variables de entorno en el build de Angular

const fs = require('fs');
const path = require('path');

// Configuración
const distPath = path.join(__dirname, 'dist/stocks-frontend');
const apiUrl = process.env.API_URL || 'http://localhost:8080';

console.log('🔧 Iniciando reemplazo de variables de entorno...');
console.log(`📍 Ruta del build: ${distPath}`);
console.log(`🌐 API URL: ${apiUrl}`);

// Verificar que la carpeta dist existe
if (!fs.existsSync(distPath)) {
  console.error('❌ Error: La carpeta dist no existe. Asegúrate de ejecutar el build primero.');
  process.exit(1);
}

// Función para procesar archivos recursivamente
function processFiles(directory) {
  const files = fs.readdirSync(directory);
  let filesProcessed = 0;

  files.forEach(file => {
    const filePath = path.join(directory, file);
    const stat = fs.statSync(filePath);

    if (stat.isDirectory()) {
      // Procesar subdirectorios recursivamente
      processFiles(filePath);
    } else if (file.endsWith('.js') || file.endsWith('.html')) {
      // Procesar archivos JS y HTML
      try {
        let content = fs.readFileSync(filePath, 'utf8');
        const originalContent = content;

        // Reemplazar la variable ${API_URL}
        content = content.replace(/\$\{API_URL\}/g, apiUrl);

        // Solo escribir si hubo cambios
        if (content !== originalContent) {
          fs.writeFileSync(filePath, content, 'utf8');
          filesProcessed++;
          console.log(`✅ Procesado: ${file}`);
        }
      } catch (error) {
        console.error(`❌ Error procesando ${file}:`, error.message);
      }
    }
  });

  return filesProcessed;
}

try {
  const totalProcessed = processFiles(distPath);
  
  if (totalProcessed > 0) {
    console.log(`\n✨ ¡Completado! ${totalProcessed} archivo(s) actualizado(s) con éxito.`);
    console.log(`🚀 La aplicación ahora apunta a: ${apiUrl}`);
  } else {
    console.log('\n⚠️  No se encontraron archivos para procesar.');
    console.log('   Verifica que el build de Angular se completó correctamente.');
  }
} catch (error) {
  console.error('\n❌ Error durante el procesamiento:', error.message);
  process.exit(1);
}
