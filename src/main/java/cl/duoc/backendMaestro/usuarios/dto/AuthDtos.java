package cl.duoc.backendMaestro.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Responsable: Camilo Alarcón
public final class AuthDtos {

    private AuthDtos() {
    }

    public record RegistroRequest(
        @NotBlank String run,
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank @Email String correo,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String region,
        @NotBlank String tipoCliente) {
    }

    public record LoginRequest(
        @NotBlank @Email String correo,
        @NotBlank String password) {}

public record UsuarioResponse(
        Long id,
        String run,
        String nombre,
        String apellido,
        String correo,
        String rol,
        String region,
        String tipoCliente,
        boolean activo) {}

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UsuarioResponse usuario) {}
}