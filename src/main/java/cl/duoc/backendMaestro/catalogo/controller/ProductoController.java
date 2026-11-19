package cl.duoc.backendMaestro.catalogo.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.backendMaestro.catalogo.dto.ProductoResponseDTO;
import cl.duoc.backendMaestro.catalogo.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoResponseDTO> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProductoResponseDTO> buscarPorCodigo(
        @PathVariable String codigo
    ) {
        return productoService.buscarPorCodigo(codigo)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
