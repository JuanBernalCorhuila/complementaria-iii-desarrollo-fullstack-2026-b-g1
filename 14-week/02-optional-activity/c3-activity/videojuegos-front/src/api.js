// Única dirección del backend en todo el frontend: si cambia, se cambia solo aquí
const API_URL = "http://localhost:8080/api";

// fetch solo falla por sí mismo cuando no hay respuesta (API apagada o bloqueada por CORS)
async function pedir(url, opciones) {
  try {
    return await fetch(url, opciones);
  } catch {
    throw new Error("No se pudo conectar con la API (está apagada o el navegador la bloqueó por CORS).");
  }
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

// GET /api/videojuegos (protegido) -> envía el token en la cabecera Authorization
export async function getVideojuegos() {
  const token = localStorage.getItem("token");
  const res = await pedir(`${API_URL}/videojuegos`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  if (res.status === 401) throw new Error("Tu sesión no es válida o ya venció (401). Inicia sesión de nuevo.");
  if (!res.ok) throw new Error("Error " + res.status);
  return res.json();
}
