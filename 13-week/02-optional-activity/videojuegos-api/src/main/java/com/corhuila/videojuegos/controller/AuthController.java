package com.corhuila.videojuegos.controller;

import com.corhuila.videojuegos.dto.LoginRequest;
import com.corhuila.videojuegos.dto.LoginResponse;
import com.corhuila.videojuegos.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Permite que el frontend (Vite, puerto 5173) haga login desde el navegador
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login que devuelve un token JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // POST /api/auth/login -> 200 OK con el token o 401 si el usuario o la clave no coinciden
    @Operation(summary = "Iniciar sesión y obtener el token JWT")
    @ApiResponse(responseCode = "200", description = "Login correcto, devuelve el token")
    @ApiResponse(responseCode = "401", description = "Usuario o clave incorrectos", content = @Content)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest datos) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(datos.usuario(), datos.clave()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new LoginResponse(jwtService.generarToken(datos.usuario())));
    }
}
