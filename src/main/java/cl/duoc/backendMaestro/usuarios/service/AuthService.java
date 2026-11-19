package cl.duoc.backendMaestro.usuarios.service;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.AuthResponse;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.LoginRequest;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.RegistroRequest;
import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.UsuarioResponse;
import cl.duoc.backendMaestro.usuarios.model.Usuario;
import cl.duoc.backendMaestro.usuarios.repository.UsuarioRepository;

// Responsable: Camilo Alarcón
@Service
public class AuthService {

    private static final long TOKEN_DURACION_SEGUNDOS = 3600;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final String issuer;
    private final String audience;

    public AuthService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        JwtEncoder jwtEncoder,
        @Value("${app.jwt.issuer}") String issuer,
        @Value("${app.jwt.audience}") String audience
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.issuer = issuer;
        this.audience = audience;
    }

    @Transactional
    public UsuarioResponse registrar(RegistroRequest request) {
        String correo = normalizarCorreo(request.correo());

        if (usuarioRepository.existsByCorreoIgnoreCase(correo)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Ya existe una cuenta con ese correo"
            );
        }

        Usuario usuario = new Usuario(
            request.nombre().trim(),
            correo,
            passwordEncoder.encode(request.password())
        );

        return convertirRespuesta(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public AuthResponse iniciarSesion(LoginRequest request) {
        String correo = normalizarCorreo(request.correo());

        Usuario usuario = usuarioRepository
            .findByCorreoIgnoreCase(correo)
            .filter(Usuario::isActivo)
            .orElseThrow(this::credencialesInvalidas);

        if (!passwordEncoder.matches(
            request.password(),
            usuario.getPasswordHash()
        )) {
            throw credencialesInvalidas();
        }

        Instant ahora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(issuer)
            .subject(usuario.getCorreo())
            .audience(List.of(audience))
            .issuedAt(ahora)
            .expiresAt(ahora.plusSeconds(TOKEN_DURACION_SEGUNDOS))
            .claim("roles", List.of(usuario.getRol()))
            .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = jwtEncoder
            .encode(JwtEncoderParameters.from(header, claims))
            .getTokenValue();

        return new AuthResponse(
            token,
            "Bearer",
            TOKEN_DURACION_SEGUNDOS,
            convertirRespuesta(usuario)
        );
    }

    private String normalizarCorreo(String correo) {
        return correo.trim().toLowerCase(Locale.ROOT);
    }

    private ResponseStatusException credencialesInvalidas() {
        return new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Correo o contraseña incorrectos"
        );
    }

    private UsuarioResponse convertirRespuesta(Usuario usuario) {
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getRol(),
            usuario.isActivo()
        );
    }
}