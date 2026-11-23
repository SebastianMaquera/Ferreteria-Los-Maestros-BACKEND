package cl.duoc.backendMaestro.usuarios.repository;

import cl.duoc.backendMaestro.usuarios.model.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Responsable: Camilo Alarcón
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombreIgnoreCase(String nombre);
}