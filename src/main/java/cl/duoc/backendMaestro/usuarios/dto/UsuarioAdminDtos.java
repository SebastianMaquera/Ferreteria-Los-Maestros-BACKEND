package cl.duoc.backendMaestro.usuarios.dto;

// Responsable: Jonathan Ulloa
public class UsuarioAdminDtos {

        public record ActualizarUsuarioRequest(
                        String nombre,
                        String apellido,
                        String correo,
                        String rol,
                        String region,
                        String tipoCliente) {
        }
}