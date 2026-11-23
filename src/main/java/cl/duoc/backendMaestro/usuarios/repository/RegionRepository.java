package cl.duoc.backendMaestro.usuarios.repository;

import cl.duoc.backendMaestro.usuarios.model.Region;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Responsable: Camilo Alarcón
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByNombreIgnoreCase(String nombre);
}