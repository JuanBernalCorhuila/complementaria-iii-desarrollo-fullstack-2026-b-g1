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

    // Listar todos
    public List<Videojuego> listar() {
        return repo.findAll();
    }

    // Buscar uno (vacio si no existe)
    public Optional<Videojuego> buscar(Long id) {
        return repo.findById(id);
    }

    // Crear
    public Videojuego guardar(Videojuego v) {
        return repo.save(v);
    }

    // Actualizar: solo si el videojuego existe, se copian los datos nuevos y se guarda
    public Optional<Videojuego> actualizar(Long id, Videojuego datos) {
        return repo.findById(id).map(v -> {
            v.setTitulo(datos.getTitulo());
            v.setGenero(datos.getGenero());
            v.setPlataforma(datos.getPlataforma());
            v.setPrecio(datos.getPrecio());
            v.setAnioLanzamiento(datos.getAnioLanzamiento());
            return repo.save(v);
        });
    }

    // Eliminar: devuelve false si el videojuego no existe
    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
