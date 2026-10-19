import { useState } from "react";
import { login } from "../api.js";

export default function Login({ onLogin }) {
  const [usuario, setUsuario] = useState("");
  const [clave, setClave] = useState("");
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState(null);

  async function enviar(e) {
    e.preventDefault();
    setCargando(true);
    setError(null);
    try {
      const token = await login(usuario, clave);
      onLogin(token);
    } catch (err) {
      setError(err.message);
      setCargando(false);
    }
  }

  return (
    <form className="tarjeta login" onSubmit={enviar}>
      <h2>Iniciar sesión</h2>

      <label htmlFor="usuario">Usuario</label>
      <input id="usuario" value={usuario} onChange={(e) => setUsuario(e.target.value)} required />

      <label htmlFor="clave">Clave</label>
      <input id="clave" type="password" value={clave} onChange={(e) => setClave(e.target.value)} required />

      <button type="submit" disabled={cargando}>
        {cargando ? "Ingresando..." : "Ingresar"}
      </button>

      {error && <p className="error">{error}</p>}
    </form>
  );
}
