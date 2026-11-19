package cl.duoc.backendMaestro.catalogo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.duoc.backendMaestro.catalogo.model.Producto;
import cl.duoc.backendMaestro.catalogo.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorCodigo(String codigo) {
        return productoRepository.findById(codigo);
    }
}
