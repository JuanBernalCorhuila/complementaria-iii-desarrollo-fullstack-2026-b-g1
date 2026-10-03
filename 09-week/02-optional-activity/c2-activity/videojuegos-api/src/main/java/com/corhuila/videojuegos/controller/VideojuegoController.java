package com.corhuila.videojuegos.controller;

import com.corhuila.videojuegos.model.Videojuego;
import com.corhuila.videojuegos.service.VideojuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
@Tag(name = "Videojuegos", description = "CRUD del catálogo de videojuegos")
public class VideojuegoController {

    private final VideojuegoService service;

    public VideojuegoController(VideojuegoService service) {
        this.service = service;
    }

    // GET /api/videojuegos -> 200 OK con la lista
    @Operation(summary = "Listar todos los videojuegos")
    @ApiResponse(responseCode = "200", description = "Lista de videojuegos")
    @GetMapping
    public List<Videojuego> listar() {
        return service.listar();
    }

    // GET /api/videojuegos/{id} -> 200 OK o 404 Not Found
    @Operation(summary = "Obtener un videojuego por su id")
    @ApiResponse(responseCode = "200", description = "Videojuego encontrado")
    @ApiResponse(responseCode = "404", description = "No existe un videojuego con ese id", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> uno(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/videojuegos -> 201 Created
    @Operation(summary = "Crear un videojuego")
    @ApiResponse(responseCode = "201", description = "Videojuego creado")
    @PostMapping
    public ResponseEntity<Videojuego> crear(@RequestBody Videojuego v) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(v));
    }

    // PUT /api/videojuegos/{id} -> 200 OK o 404 Not Found
    @Operation(summary = "Actualizar un videojuego")
    @ApiResponse(responseCode = "200", description = "Videojuego actualizado")
    @ApiResponse(responseCode = "404", description = "No existe un videojuego con ese id", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> actualizar(@PathVariable Long id, @RequestBody Videojuego v) {
        return service.actualizar(id, v)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/videojuegos/{id} -> 204 No Content o 404 Not Found
    @Operation(summary = "Borrar un videojuego")
    @ApiResponse(responseCode = "204", description = "Videojuego borrado", content = @Content)
    @ApiResponse(responseCode = "404", description = "No existe un videojuego con ese id", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
