package com.corhuila.videojuegos.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Prueba de INTEGRACIÓN: se levanta la aplicación completa (seguridad, controller, service,
// repository y H2) y MockMvc hace las peticiones HTTP sin necesidad de abrir el puerto 8080.
@SpringBootTest
@AutoConfigureMockMvc
class VideojuegoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    // Antes de cada prueba se hace login de verdad para obtener un token válido
    @BeforeEach
    void obtenerToken() throws Exception {
        String respuesta = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usuario\":\"admin\",\"clave\":\"admin123\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        token = JsonPath.read(respuesta, "$.token");
    }

    // Camino feliz: con token, el listado responde 200 con los dos videojuegos que se cargan al arrancar
    @Test
    void listar_conToken_responde200ConLosDosVideojuegos() throws Exception {
        mockMvc.perform(get("/api/videojuegos").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("Red Dead Redemption 2"))
                .andExpect(jsonPath("$[1].titulo").value("Forza Horizon 5"));
    }

    // Caso de error: un id que no existe responde 404
    @Test
    void buscar_idInexistente_responde404() throws Exception {
        mockMvc.perform(get("/api/videojuegos/99").header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    // Caso de error: sin token la seguridad no deja pasar y responde 401
    @Test
    void listar_sinToken_responde401() throws Exception {
        mockMvc.perform(get("/api/videojuegos"))
                .andExpect(status().isUnauthorized());
    }
}
