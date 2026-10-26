package com.corhuila.videojuegos.service;

import com.corhuila.videojuegos.model.Videojuego;
import com.corhuila.videojuegos.repository.VideojuegoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Prueba UNITARIA: solo se prueba el service. El repositorio es un mock (falso),
// así que no se levanta Spring ni se usa la base de datos.
@ExtendWith(MockitoExtension.class)
class VideojuegoServiceTest {

    @Mock
    private VideojuegoRepository repo;

    @InjectMocks
    private VideojuegoService service;

    // Camino feliz: el videojuego existe, se copian los datos nuevos y se guarda
    @Test
    void actualizar_idExistente_copiaLosDatosYGuarda() {
        Videojuego actual = new Videojuego("Forza Horizon 5", "Carreras", "Xbox Series X", 189900, 2021);
        Videojuego nuevos = new Videojuego("Forza Horizon 5", "Carreras", "PC", 159900, 2021);
        when(repo.findById(2L)).thenReturn(Optional.of(actual));
        when(repo.save(actual)).thenReturn(actual);

        Optional<Videojuego> resultado = service.actualizar(2L, nuevos);

        assertTrue(resultado.isPresent());
        assertEquals("PC", resultado.get().getPlataforma());
        assertEquals(159900.0, resultado.get().getPrecio());
        verify(repo).save(actual);
    }

    // Caso de error: el id no existe, devuelve vacío (el controller lo convierte en 404) y no guarda nada
    @Test
    void actualizar_idInexistente_devuelveVacioYNoGuarda() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Videojuego> resultado = service.actualizar(99L, new Videojuego());

        assertTrue(resultado.isEmpty());
        verify(repo, never()).save(any());
    }

    // Caso de error: borrar un id que no existe devuelve false y no llama a deleteById
    @Test
    void eliminar_idInexistente_devuelveFalseYNoBorra() {
        when(repo.existsById(99L)).thenReturn(false);

        assertFalse(service.eliminar(99L));
        verify(repo, never()).deleteById(any());
    }
}
