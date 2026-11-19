package cl.duoc.backendMaestro.catalogo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.duoc.backendMaestro.catalogo.dto.ProductoResponseDTO;
import cl.duoc.backendMaestro.catalogo.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarProductos() {
        return productoRepository.findAll()
            .stream()
            .map(ProductoResponseDTO::desde)
            .toList();
    }

    @Transactional(readOnly = true)
    public Optional<ProductoResponseDTO> buscarPorCodigo(String codigo) {
        return productoRepository.findById(codigo)
            .map(ProductoResponseDTO::desde);
    }
}

