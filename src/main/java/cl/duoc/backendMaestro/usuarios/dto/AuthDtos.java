package cl.duoc.backendMaestro.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Responsable: Camilo Alarcón
public final class AuthDtos {

    private AuthDtos() {
    }

    public record RegistroRequest(
        @NotBlank @Size(max = 120) String nombre,
        @NotBlank @Email @Size(max = 150) String correo,
        @NotBlank @Size(min = 8) String password
    ) {
    }

    public record LoginRequest(
        @NotBlank @Email String correo,
        @NotBlank String password
    ) {
    }

    public record UsuarioResponse(
        Long id,
        String nombre,
        String correo,
        String rol,
        boolean activo
    ) {
    }

    public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds,
        UsuarioResponse usuario
    ) {
    }
}