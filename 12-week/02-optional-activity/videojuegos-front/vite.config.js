import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// Puerto fijo 5173: es el origen que la API tiene permitido en CORS
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    strictPort: true,
  },
});
