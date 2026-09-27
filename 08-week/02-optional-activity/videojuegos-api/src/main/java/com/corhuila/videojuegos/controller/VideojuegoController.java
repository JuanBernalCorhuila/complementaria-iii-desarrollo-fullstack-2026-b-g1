package com.corhuila.videojuegos.controller;

import com.corhuila.videojuegos.model.Videojuego;
import com.corhuila.videojuegos.service.VideojuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final VideojuegoService service;

    public VideojuegoController(VideojuegoService service) {
        this.service = service;
    }

    // GET /api/videojuegos -> 200 OK con la lista
    @GetMapping
    public List<Videojuego> listar() {
        return service.listar();
    }

    // GET /api/videojuegos/{id} -> 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> uno(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/videojuegos -> 201 Created
    @PostMapping
    public ResponseEntity<Videojuego> crear(@RequestBody Videojuego v) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(v));
    }

    // PUT /api/videojuegos/{id} -> 200 OK o 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> actualizar(@PathVariable Long id, @RequestBody Videojuego v) {
        return service.actualizar(id, v)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/videojuegos/{id} -> 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
