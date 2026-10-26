import { useState } from "react";
import Login from "./components/Login.jsx";
import Videojuegos from "./components/Videojuegos.jsx";

export default function App() {
  // Si ya hay un token guardado, se entra directo al catálogo
  const [token, setToken] = useState(localStorage.getItem("token"));

  function cerrarSesion() {
    localStorage.removeItem("token");
    setToken(null);
  }

  return (
    <main className="contenedor">
      <h1>Catálogo de Videojuegos</h1>
      {token ? <Videojuegos onCerrarSesion={cerrarSesion} /> : <Login onLogin={setToken} />}
    </main>
  );
}
