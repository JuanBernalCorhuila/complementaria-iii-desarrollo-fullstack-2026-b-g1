import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// Puerto fijo 5173: es el origen que la API tiene permitido en CORS
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    strictPort: true,
  },
  // Configuración de las pruebas (Vitest)
  test: {
    environment: "jsdom",          // simula el navegador (document, window) dentro de Node
    globals: true,                 // permite que Testing Library limpie la pantalla después de cada prueba
    setupFiles: "./src/setupTests.js",
  },
});
