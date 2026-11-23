package cl.duoc.backendMaestro.usuarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "regiones", uniqueConstraints = @UniqueConstraint(name = "uk_regiones_nombre", columnNames = "nombre"))
// Responsable: Camilo Alarcón
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    protected Region() {
    }

    public Region(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}