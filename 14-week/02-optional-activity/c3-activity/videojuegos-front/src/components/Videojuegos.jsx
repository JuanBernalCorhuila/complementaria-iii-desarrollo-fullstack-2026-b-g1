import { useEffect, useState } from "react";
import { getVideojuegos } from "../api.js";

export default function Videojuegos({ onCerrarSesion }) {
  const [videojuegos, setVideojuegos] = useState([]);
  const [estado, setEstado] = useState("cargando"); // "cargando" | "ok" | "error"
  const [error, setError] = useState(null);

  // Al mostrarse la pantalla se piden los videojuegos a la API
  useEffect(() => {
    getVideojuegos()
      .then((datos) => {
        setVideojuegos(datos);
        setEstado("ok");
      })
      .catch((err) => {
        setError(err.message);
        setEstado("error");
      });
  }, []);

  return (
    <section className="tarjeta">
      <div className="encabezado">
        <h2>Videojuegos</h2>
        <button onClick={onCerrarSesion}>Cerrar sesión</button>
      </div>

      {estado === "cargando" && <p className="cargando">Cargando videojuegos...</p>}

      {estado === "error" && <p className="error">{error}</p>}

      {estado === "ok" && videojuegos.length === 0 && <p>No hay videojuegos registrados.</p>}

      {estado === "ok" && videojuegos.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Título</th>
              <th>Género</th>
              <th>Plataforma</th>
              <th>Año</th>
              <th>Precio</th>
            </tr>
          </thead>
          <tbody>
            {videojuegos.map((v) => (
              <tr key={v.id}>
                <td>{v.id}</td>
                <td>{v.titulo}</td>
                <td>{v.genero}</td>
                <td>{v.plataforma}</td>
                <td>{v.anioLanzamiento}</td>
                <td>${v.precio.toLocaleString("es-CO")}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
