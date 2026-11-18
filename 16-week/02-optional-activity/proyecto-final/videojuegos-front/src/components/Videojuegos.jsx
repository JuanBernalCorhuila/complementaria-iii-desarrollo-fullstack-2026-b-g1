import { useEffect, useState } from "react";
import { getVideojuegos, crearVideojuego, actualizarVideojuego, eliminarVideojuego } from "../api.js";
import VideojuegoForm from "./VideojuegoForm.jsx";

export default function Videojuegos({ onCerrarSesion }) {
  const [videojuegos, setVideojuegos] = useState([]);
  const [estado, setEstado] = useState("cargando"); // "cargando" | "ok" | "error"
  const [error, setError] = useState(null);
  const [editando, setEditando] = useState(null); // videojuego que se está editando (null = el formulario crea uno nuevo)
  const [errorBorrar, setErrorBorrar] = useState(null);

  // Pide la lista a la API; se usa al abrir la pantalla y después de cada cambio
  function cargar() {
    getVideojuegos()
      .then((datos) => {
        setVideojuegos(datos);
        setEstado("ok");
      })
      .catch((err) => {
        setError(err.message);
        setEstado("error");
      });
  }

  useEffect(() => {
    cargar();
  }, []);

  // Crear o actualizar según si hay un videojuego en edición. Si falla, el formulario muestra el error
  async function guardar(datos) {
    if (editando) {
      await actualizarVideojuego(editando.id, datos);
    } else {
      await crearVideojuego(datos);
    }
    setEditando(null);
    cargar();
  }

  async function borrar(v) {
    if (!window.confirm(`¿Borrar "${v.titulo}"?`)) return;
    setErrorBorrar(null);
    try {
      await eliminarVideojuego(v.id);
      if (editando?.id === v.id) setEditando(null);
      cargar();
    } catch (err) {
      setErrorBorrar(err.message);
    }
  }

  return (
    <section className="tarjeta">
      <div className="encabezado">
        <h2>Videojuegos</h2>
        <button onClick={onCerrarSesion}>Cerrar sesión</button>
      </div>

      {/* El key hace que el formulario se reinicie al cambiar entre crear y editar */}
      <VideojuegoForm
        key={editando ? editando.id : "nuevo"}
        videojuego={editando}
        onGuardar={guardar}
        onCancelar={() => setEditando(null)}
      />

      {estado === "cargando" && <p className="cargando">Cargando videojuegos...</p>}

      {estado === "error" && <p className="error">{error}</p>}

      {errorBorrar && <p className="error">{errorBorrar}</p>}

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
              <th>Acciones</th>
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
                <td className="acciones">
                  <button className="secundario" onClick={() => setEditando(v)}>Editar</button>
                  <button className="peligro" onClick={() => borrar(v)}>Borrar</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
