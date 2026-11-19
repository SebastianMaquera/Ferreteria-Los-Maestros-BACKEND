package cl.duoc.backendMaestro.usuarios.service;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.UsuarioResponse;
import cl.duoc.backendMaestro.usuarios.dto.UsuarioAdminDtos.ActualizarUsuarioRequest;
import cl.duoc.backendMaestro.usuarios.model.Usuario;
import cl.duoc.backendMaestro.usuarios.repository.UsuarioRepository;

// Responsable: Camilo Alarcón
@Service
public class UsuariosAdminService {

    private final UsuarioRepository usuarioRepository;

    public UsuariosAdminService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
            .stream()
            .map(this::respuesta)
            .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscar(Long id) {
        return respuesta(buscarEntidad(id));
    }

    @Transactional
    public UsuarioResponse actualizar(
        Long id,
        ActualizarUsuarioRequest request
    ) {
        Usuario usuario = buscarEntidad(id);
        String correo = request.correo().trim().toLowerCase(Locale.ROOT);

        boolean correoUsadoPorOtroUsuario =
            usuarioRepository.existsByCorreoIgnoreCase(correo)
            && !usuario.getCorreo().equalsIgnoreCase(correo);

        if (correoUsadoPorOtroUsuario) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Ese correo ya pertenece a otro usuario"
            );
        }

        usuario.actualizarDatos(
            request.nombre().trim(),
            correo,
            request.rol()
        );

        return respuesta(usuario);
    }

    @Transactional
    public void desactivar(Long id) {
        Usuario usuario = buscarEntidad(id);
        usuario.desactivar();
    }

    private Usuario buscarEntidad(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuario no encontrado"
            ));
    }

    private UsuarioResponse respuesta(Usuario usuario) {
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getRol(),
            usuario.isActivo()
        );
    }
}