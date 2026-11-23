package cl.duoc.backendMaestro.usuarios.service;

import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.UsuarioResponse;
import cl.duoc.backendMaestro.usuarios.dto.UsuarioAdminDtos.ActualizarUsuarioRequest;
import cl.duoc.backendMaestro.usuarios.model.Region;
import cl.duoc.backendMaestro.usuarios.model.Rol;
import cl.duoc.backendMaestro.usuarios.model.Usuario;
import cl.duoc.backendMaestro.usuarios.repository.RegionRepository;
import cl.duoc.backendMaestro.usuarios.repository.RolRepository;
import cl.duoc.backendMaestro.usuarios.repository.UsuarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
// Responsable: Camilo Alarcón
public class UsuariosAdminService {

        private final UsuarioRepository usuarioRepository;
        private final RolRepository rolRepository;
        private final RegionRepository regionRepository;

        public UsuariosAdminService(
                        UsuarioRepository usuarioRepository,
                        RolRepository rolRepository,
                        RegionRepository regionRepository) {
                this.usuarioRepository = usuarioRepository;
                this.rolRepository = rolRepository;
                this.regionRepository = regionRepository;
        }

        @Transactional(readOnly = true)
        public List<UsuarioResponse> listar() {
                return usuarioRepository.findAll()
                                .stream()
                                .map(this::convertirRespuesta)
                                .toList();
        }

        @Transactional
        public UsuarioResponse actualizar(Long id, ActualizarUsuarioRequest request) {
                Usuario usuario = usuarioRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "No se encontró el usuario."));

                usuarioRepository.findByCorreoIgnoreCase(request.correo())
                                .filter(otro -> !otro.getId().equals(id))
                                .ifPresent(otro -> {
                                        throw new ResponseStatusException(
                                                        HttpStatus.CONFLICT, "Ese correo ya está registrado.");
                                });

                Rol rol = rolRepository.findByNombreIgnoreCase(request.rol())
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST, "El rol indicado no existe."));

                Region region = regionRepository.findByNombreIgnoreCase(request.region())
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST, "La región indicada no existe."));

                usuario.actualizarDatos(
                                request.nombre(),
                                request.apellido(),
                                request.correo(),
                                request.tipoCliente(),
                                rol,
                                region);
                return convertirRespuesta(usuarioRepository.save(usuario));
        }

        @Transactional
        public void desactivar(Long id) {
                Usuario usuario = usuarioRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "No se encontró el usuario."));

                usuario.desactivar();
                usuarioRepository.save(usuario);
        }

        private UsuarioResponse convertirRespuesta(Usuario usuario) {
                return new UsuarioResponse(
                                usuario.getId(),
                                usuario.getRun(),
                                usuario.getNombre(),
                                usuario.getApellido(),
                                usuario.getCorreo(),
                                usuario.getRol(),
                                usuario.getRegion().getNombre(),
                                usuario.getTipoCliente(),
                                usuario.isActivo());
        }
       
        @Transactional(readOnly = true)
        public UsuarioResponse buscar(Long id) {
                Usuario usuario = usuarioRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "No se encontró el usuario."));

                return convertirRespuesta(usuario);
        }
}