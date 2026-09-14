package com.corhuila.videojuegos.repository;

import com.corhuila.videojuegos.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    // Consulta por método: Spring Data genera el SQL solo con el nombre
    List<Videojuego> findByGenero(String genero);
}
