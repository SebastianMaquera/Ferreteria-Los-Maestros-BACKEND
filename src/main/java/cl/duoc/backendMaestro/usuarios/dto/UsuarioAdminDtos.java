package cl.duoc.backendMaestro.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Responsable: Camilo Alarcón
public final class UsuarioAdminDtos {

    private UsuarioAdminDtos() {
    }

    public record ActualizarUsuarioRequest(
        @NotBlank @Size(max = 120) String nombre,
        @NotBlank @Email @Size(max = 150) String correo,
        @NotBlank
        @Pattern(regexp = "ADMIN|VENDEDOR|CLIENTE")
        String rol
    ) {
    }
}