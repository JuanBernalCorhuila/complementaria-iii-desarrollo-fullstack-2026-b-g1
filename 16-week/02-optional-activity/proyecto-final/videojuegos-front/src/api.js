// La dirección de la API sale de la variable de entorno VITE_API_URL:
// con "npm run dev" se toma de .env.development y en Docker llega al construir la imagen
const API_URL = import.meta.env.VITE_API_URL;

// fetch solo falla por sí mismo cuando no hay respuesta (API apagada o bloqueada por CORS)
async function pedir(url, opciones) {
  try {
    return await fetch(url, opciones);
  } catch {
    throw new Error("No se pudo conectar con la API (está apagada o el navegador la bloqueó por CORS).");
  }
}

// Cabeceras de las peticiones protegidas: el cuerpo va en JSON y el token en Authorization
function cabeceras() {
  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  };
}

// Convierte los códigos de error de la API en mensajes claros para el usuario
function revisar(res) {
  if (res.status === 401) throw new Error("Tu sesión no es válida o ya venció (401). Inicia sesión de nuevo.");
  if (res.status === 404) throw new Error("Ese videojuego ya no existe (404).");
  if (!res.ok) throw new Error("Error " + res.status);
}

// POST /api/auth/login -> guarda el token en localStorage y lo devuelve
export async function login(usuario, clave) {
  const res = await pedir(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ usuario, clave }),
  });
  if (res.status === 401) throw new Error("Usuario o clave incorrectos.");
  if (!res.ok) throw new Error("Error " + res.status);

  const datos = await res.json();
  localStorage.setItem("token", datos.token);
  return datos.token;
}

// GET /api/videojuegos -> lista del catálogo
export async function getVideojuegos() {
  const res = await pedir(`${API_URL}/videojuegos`, { headers: cabeceras() });
  revisar(res);
  return res.json();
}

// POST /api/videojuegos -> 201 con el videojuego creado
export async function crearVideojuego(videojuego) {
  const res = await pedir(`${API_URL}/videojuegos`, {
    method: "POST",
    headers: cabeceras(),
    body: JSON.stringify(videojuego),
  });
  revisar(res);
  return res.json();
}

// PUT /api/videojuegos/{id} -> 200 con el videojuego actualizado
export async function actualizarVideojuego(id, videojuego) {
  const res = await pedir(`${API_URL}/videojuegos/${id}`, {
    method: "PUT",
    headers: cabeceras(),
    body: JSON.stringify(videojuego),
  });
  revisar(res);
  return res.json();
}

// DELETE /api/videojuegos/{id} -> 204 sin cuerpo
export async function eliminarVideojuego(id) {
  const res = await pedir(`${API_URL}/videojuegos/${id}`, {
    method: "DELETE",
    headers: cabeceras(),
  });
  revisar(res);
}
