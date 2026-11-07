import { render, screen } from "@testing-library/react";
import { describe, test, expect, vi } from "vitest";
import Videojuegos from "./Videojuegos.jsx";
import { getVideojuegos } from "../api.js";

// Se reemplaza api.js por una versión falsa: la prueba no depende de que el backend esté encendido
// y cada caso decide qué "responde" la API.
vi.mock("../api.js");

describe("Componente Videojuegos", () => {
  // Camino feliz: primero se ve el mensaje de carga y luego la tabla con los datos
  test("muestra la tabla con los videojuegos que devuelve la API", async () => {
    getVideojuegos.mockResolvedValue([
      { id: 1, titulo: "Red Dead Redemption 2", genero: "Aventura", plataforma: "PS4", precio: 149900, anioLanzamiento: 2018 },
      { id: 2, titulo: "Forza Horizon 5", genero: "Carreras", plataforma: "Xbox Series X", precio: 189900, anioLanzamiento: 2021 },
    ]);

    render(<Videojuegos onCerrarSesion={() => {}} />);

    expect(screen.getByText("Cargando videojuegos...")).toBeInTheDocument();
    expect(await screen.findByText("Red Dead Redemption 2")).toBeInTheDocument();
    expect(screen.getByText("Forza Horizon 5")).toBeInTheDocument();
    expect(screen.queryByText("Cargando videojuegos...")).not.toBeInTheDocument();
  });

  // Caso de error: la API rechaza el token (401) y se muestra el mensaje en lugar de la tabla
  test("muestra el mensaje de error cuando la API responde 401", async () => {
    getVideojuegos.mockRejectedValue(
      new Error("Tu sesión no es válida o ya venció (401). Inicia sesión de nuevo.")
    );

    render(<Videojuegos onCerrarSesion={() => {}} />);

    expect(await screen.findByText(/no es válida o ya venció/)).toBeInTheDocument();
    expect(screen.queryByRole("table")).not.toBeInTheDocument();
  });

  // Caso límite: la API responde una lista vacía
  test("avisa cuando no hay videojuegos registrados", async () => {
    getVideojuegos.mockResolvedValue([]);

    render(<Videojuegos onCerrarSesion={() => {}} />);

    expect(await screen.findByText("No hay videojuegos registrados.")).toBeInTheDocument();
    expect(screen.queryByRole("table")).not.toBeInTheDocument();
  });
});
