package com.corhuila.videojuegos;

import com.corhuila.videojuegos.model.Videojuego;
import com.corhuila.videojuegos.repository.VideojuegoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VideojuegosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideojuegosApiApplication.class, args);
    }

    // H2 arranca vacia: se cargan dos videojuegos para que el frontend tenga datos que mostrar
    @Bean
    CommandLineRunner datosIniciales(VideojuegoRepository repo) {
        return args -> {
            repo.save(new Videojuego("Red Dead Redemption 2", "Aventura", "PS4", 149900, 2018));
            repo.save(new Videojuego("Forza Horizon 5", "Carreras", "Xbox Series X", 189900, 2021));
        };
    }
}
