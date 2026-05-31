package territorial.infrastructure.adapter.in.web;

import territorial.application.port.in.GestionarLugarUseCase;
import territorial.domain.model.LugarProduccion;
import territorial.infrastructure.adapter.in.web.dto.LugarRequest;
import territorial.infrastructure.adapter.in.web.dto.LugarResponse;
import territorial.infrastructure.adapter.in.web.mapper.TerritorialWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
/**
 * Controlador REST que expone los endpoints de gestión de lugares de producción.
 *
 * <p>Base URL: {@code /api/territorial/lugares}.</p>
 */

@RestController
@RequestMapping("/lugares")
@RequiredArgsConstructor
@Tag(name = "Lugares de ProducciÃ³n", description = "CRUD de lugares de producciÃ³n agrÃ­cola")
public class LugarController {

    private final GestionarLugarUseCase gestionarLugar;
    private final TerritorialWebMapper mapper;

    @GetMapping
    @Operation(summary = "Listar todos los lugares de producciÃ³n")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<LugarResponse>> listar(
            @RequestParam(required = false) Long municipioId) {
        List<LugarProduccion> lugares = municipioId != null
                ? gestionarLugar.listarPorMunicipio(municipioId)
                : gestionarLugar.listarTodos();
        return ResponseEntity.ok(mapper.toLugarResponseList(lugares));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener lugar de producciÃ³n por ID")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LugarResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLugar.obtenerPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Crear lugar de producciÃ³n")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LugarResponse> crear(@Valid @RequestBody LugarRequest request) {
        LugarProduccion creado = gestionarLugar.crear(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/territorial/lugares/" + creado.getId()))
                .body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar lugar de producciÃ³n")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LugarResponse> actualizar(
            @PathVariable Long id, @Valid @RequestBody LugarRequest request) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLugar.actualizar(id, mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lugar de producciÃ³n")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarLugar.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activar lugar de producciÃ³n")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LugarResponse> activar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLugar.activar(id)));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar lugar de producciÃ³n")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LugarResponse> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLugar.desactivar(id)));
    }
}

