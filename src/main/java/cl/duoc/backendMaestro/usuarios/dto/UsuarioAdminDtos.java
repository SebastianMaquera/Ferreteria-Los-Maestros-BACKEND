package cl.duoc.backendMaestro.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Responsable: Jonathan Ulloa
public class UsuarioAdminDtos {

        public record ActualizarUsuarioRequest(
                        @NotBlank String nombre,
                        @NotBlank String apellido,
                        @NotBlank @Email String correo,
                        @NotBlank String rol,
                        @NotBlank String region,
                        @NotBlank String tipoCliente) {
        }
}