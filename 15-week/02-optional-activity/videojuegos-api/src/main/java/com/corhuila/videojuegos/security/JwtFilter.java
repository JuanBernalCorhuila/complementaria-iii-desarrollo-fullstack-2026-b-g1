package com.corhuila.videojuegos.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Se crea en SecurityConfig para que solo se ejecute dentro de la cadena de seguridad
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        // Solo se revisa si viene la cabecera "Authorization: Bearer <token>"
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String usuario = jwtService.obtenerUsuario(token);
                // Token valido: se marca la peticion como autenticada
                var auth = new UsernamePasswordAuthenticationToken(usuario, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException | IllegalArgumentException e) {
                // Token invalido o vencido: la peticion sigue sin autenticar y Spring Security responde 401
            }
        }

        chain.doFilter(request, response);
    }
}
