package cl.duoc.backendMaestro.usuarios.service;

import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.AuthResponse;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.LoginRequest;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.RegistroRequest;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.UsuarioResponse;
import cl.duoc.backendMaestro.usuarios.model.Region;
import cl.duoc.backendMaestro.usuarios.model.Rol;
import cl.duoc.backendMaestro.usuarios.model.Usuario;
import cl.duoc.backendMaestro.usuarios.repository.RegionRepository;
import cl.duoc.backendMaestro.usuarios.repository.RolRepository;
import cl.duoc.backendMaestro.usuarios.repository.UsuarioRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
// Responsable: Camilo Alarcón
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Value("${app.jwt.issuer}")
    private String issuer;

    @Value("${app.jwt.audience}")
    private String audience;

    public AuthService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            RegionRepository regionRepository,
            PasswordEncoder passwordEncoder,
            JwtEncoder jwtEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.regionRepository = regionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Transactional
    public UsuarioResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByCorreoIgnoreCase(request.correo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe una cuenta con ese correo.");
        }

        if (usuarioRepository.existsByRunIgnoreCase(request.run())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe una cuenta con ese RUN.");
        }

        Rol rolCliente = rolRepository.findByNombreIgnoreCase("CLIENTE")
                .orElseThrow(() -> new IllegalStateException(
                        "No está configurado el rol CLIENTE."));

        Region region = regionRepository.findByNombreIgnoreCase(request.region())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "La región indicada no existe."));

        Usuario usuario = new Usuario(
                        request.run(),
                        request.nombre(),
                        request.apellido(),
                        request.correo(),
                        passwordEncoder.encode(request.password()),
                        request.tipoCliente(),
                        rolCliente,
                        region);

        return convertirRespuesta(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public AuthResponse iniciarSesion(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(request.correo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos."));

        if (!usuario.isActivo()
                || !passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos.");
        }

        Instant ahora = Instant.now();
        Instant expiracion = ahora.plus(1, ChronoUnit.HOURS);
        String nombreRol = usuario.getRol();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(usuario.getCorreo())
                .audience(List.of(audience))
                .issuedAt(ahora)
                .expiresAt(expiracion)
                .claim("roles", List.of(nombreRol))
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)).getTokenValue();

        return new AuthResponse(
                token,
                "Bearer",
                3600,
                convertirRespuesta(usuario));
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
}