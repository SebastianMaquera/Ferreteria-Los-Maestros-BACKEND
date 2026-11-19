package cl.duoc.backendMaestro.catalogo.dto;

import java.math.BigDecimal;
import cl.duoc.backendMaestro.catalogo.model.Producto;

public record ProductoResponseDTO(
    String codigo,
    String nombre,
    String categoria,
    String subcategoria,
    String marca,
    String unidad,
    BigDecimal precioCompra,
    BigDecimal precioVenta,
    Integer stock,
    Integer stockMinimo,
    boolean destacado,
    String imagen
) {
    public static ProductoResponseDTO desde(Producto producto) {
        return new ProductoResponseDTO(
            producto.getCodigo(),
            producto.getNombre(),
            producto.getSubcategoria().getCategoria().getNombre(),
            producto.getSubcategoria().getNombre(),
            producto.getMarca(),
            producto.getUnidad(),
            producto.getPrecioCompra(),
            producto.getPrecioVenta(),
            producto.getStock(),
            producto.getStockMinimo(),
            producto.isDestacado(),
            producto.getImagen()
        );
    }
}
