package com.corhuila.videojuegos.repository;

import com.corhuila.videojuegos.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {
}
