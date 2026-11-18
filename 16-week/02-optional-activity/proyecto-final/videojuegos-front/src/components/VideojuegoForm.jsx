import { useState } from "react";

const VACIO = { titulo: "", genero: "", plataforma: "", precio: "", anioLanzamiento: "" };

// Formulario para crear un videojuego o, si recibe uno, para editarlo
export default function VideojuegoForm({ videojuego, onGuardar, onCancelar }) {
  const [datos, setDatos] = useState(videojuego ?? VACIO);
  const [guardando, setGuardando] = useState(false);
  const [error, setError] = useState(null);

  // Un solo manejador para todos los campos: usa el "name" del input
  function cambiar(e) {
    setDatos({ ...datos, [e.target.name]: e.target.value });
  }

  async function enviar(e) {
    e.preventDefault();
    setGuardando(true);
    setError(null);
    try {
      // Los inputs devuelven texto: precio y año se convierten a número, como los espera la API
      await onGuardar({
        titulo: datos.titulo,
        genero: datos.genero,
        plataforma: datos.plataforma,
        precio: Number(datos.precio),
        anioLanzamiento: Number(datos.anioLanzamiento),
      });
      setDatos(VACIO);
    } catch (err) {
      setError(err.message);
    }
    setGuardando(false);
  }

  return (
    <form className="formulario" onSubmit={enviar}>
      <h3>{videojuego ? `Editar videojuego #${videojuego.id}` : "Nuevo videojuego"}</h3>

      <label htmlFor="titulo">Título</label>
      <input id="titulo" name="titulo" value={datos.titulo} onChange={cambiar} required />

      <label htmlFor="genero">Género</label>
      <input id="genero" name="genero" value={datos.genero} onChange={cambiar} required />

      <label htmlFor="plataforma">Plataforma</label>
      <input id="plataforma" name="plataforma" value={datos.plataforma} onChange={cambiar} required />

      <label htmlFor="precio">Precio</label>
      <input id="precio" name="precio" type="number" min="0" value={datos.precio} onChange={cambiar} required />

      <label htmlFor="anioLanzamiento">Año</label>
      <input id="anioLanzamiento" name="anioLanzamiento" type="number" min="1950" max="2100"
        value={datos.anioLanzamiento} onChange={cambiar} required />

      <div className="acciones">
        <button type="submit" disabled={guardando}>
          {guardando ? "Guardando..." : videojuego ? "Guardar cambios" : "Agregar"}
        </button>
        {videojuego && (
          <button type="button" className="secundario" onClick={onCancelar}>Cancelar</button>
        )}
      </div>

      {error && <p className="error">{error}</p>}
    </form>
  );
}
