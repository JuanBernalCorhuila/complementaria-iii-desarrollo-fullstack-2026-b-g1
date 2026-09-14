package com.corhuila.videojuegos.service;

import com.corhuila.videojuegos.model.Videojuego;
import com.corhuila.videojuegos.repository.VideojuegoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideojuegoService {

    private final VideojuegoRepository repo;

    public VideojuegoService(VideojuegoRepository repo) {
        this.repo = repo;
    }

    // Create
    public Videojuego crear(Videojuego v) {
        return repo.save(v);
    }

    // Read (todos)
    public List<Videojuego> listar() {
        return repo.findAll();
    }

    // Read (uno)
    public Optional<Videojuego> buscarPorId(Long id) {
        return repo.findById(id);
    }

    // Read (por género, consulta personalizada)
    public List<Videojuego> buscarPorGenero(String genero) {
        return repo.findByGenero(genero);
    }

    // Update (save() con id existente actualiza en vez de crear)
    public Videojuego actualizar(Videojuego v) {
        return repo.save(v);
    }

    // Delete
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
