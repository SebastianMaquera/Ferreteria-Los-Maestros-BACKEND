package cl.duoc.backendMaestro.usuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.AuthResponse;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.LoginRequest;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.RegistroRequest;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.UsuarioResponse;
import cl.duoc.backendMaestro.usuarios.service.AuthService;

// Responsable: Camilo Alarcón
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrar(
        @Valid @RequestBody RegistroRequest request
    ) {
        UsuarioResponse usuario = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public AuthResponse iniciarSesion(
        @Valid @RequestBody LoginRequest request
    ) {
        return authService.iniciarSesion(request);
    }
}