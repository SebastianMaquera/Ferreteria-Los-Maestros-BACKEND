package cl.duoc.backendMaestro.usuarios.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_run", columnNames = "run"),
        @UniqueConstraint(name = "uk_usuarios_correo", columnNames = "correo")
})
// Responsable: Camilo Alarcón
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, length = 15)
    private String run;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 80)
    private String apellido;

    @Column(nullable = false, length = 120)
    private String correo;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Column(name = "tipo_cliente", length = 20)
    private String tipoCliente;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_region", nullable = false)
    private Region region;

    protected Usuario() {
    }

    public Usuario(
            String run,
            String nombre,
            String apellido,
            String correo,
            String passwordHash,
            String tipoCliente,
            Rol rol,
            Region region) {
        this.run = run;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.tipoCliente = tipoCliente;
        this.rol = rol;
        this.region = region;
    }

    public void actualizarDatos(String nombre, String correo, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    public void desactivar() {
        this.activo = false;
    }

    public Long getId() {
        return id;
    }

    public String getRun() {
        return run;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public String getRol() {
        return rol.getNombre();
    }

    public Region getRegion() {
        return region;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
   
    public void actualizarDatos(
            String nombre,
            String apellido,
            String correo,
            String tipoCliente,
            Rol rol,
            Region region) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.tipoCliente = tipoCliente;
        this.rol = rol;
        this.region = region;
    }
}