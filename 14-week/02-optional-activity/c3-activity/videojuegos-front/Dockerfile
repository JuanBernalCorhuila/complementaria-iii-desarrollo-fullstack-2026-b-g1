# ---------- Etapa 1: construir la aplicacion de React ----------
# Vite 7 necesita Node 20.19 o superior
FROM node:22-alpine AS build
WORKDIR /build
COPY package.json .
RUN npm install
COPY . .
# Genera la carpeta dist/ con el HTML, CSS y JS ya listos
RUN npm run build

# ---------- Etapa 2: servir esos archivos con nginx ----------
FROM nginx:alpine
COPY --from=build /build/dist /usr/share/nginx/html
EXPOSE 80
