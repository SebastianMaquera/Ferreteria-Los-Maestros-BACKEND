package cl.duoc.backendMaestro.usuarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import cl.duoc.backendMaestro.usuarios.dto.AuthDtos.UsuarioResponse;
import cl.duoc.backendMaestro.usuarios.dto.UsuarioAdminDtos.ActualizarUsuarioRequest;
import cl.duoc.backendMaestro.usuarios.service.UsuariosAdminService;

// Responsable: Camilo Alarcón
@RestController
@RequestMapping("/api/admin/usuarios")
public class UsuariosAdminController {

    private final UsuariosAdminService usuariosAdminService;

    public UsuariosAdminController(
        UsuariosAdminService usuariosAdminService
    ) {
        this.usuariosAdminService = usuariosAdminService;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuariosAdminService.listar();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscar(@PathVariable Long id) {
        return usuariosAdminService.buscar(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizar(
        @PathVariable Long id,
        @Valid @RequestBody ActualizarUsuarioRequest request
    ) {
        return usuariosAdminService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        usuariosAdminService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}