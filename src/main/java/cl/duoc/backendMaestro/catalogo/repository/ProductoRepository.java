package cl.duoc.backendMaestro.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.backendMaestro.catalogo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, String> {
}
