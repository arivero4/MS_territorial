package territorial.infrastructure.adapter.in.web;

import territorial.application.port.in.GestionarCultivoUseCase;
import territorial.domain.model.Cultivo;
import territorial.infrastructure.adapter.in.web.dto.CultivoRequest;
import territorial.infrastructure.adapter.in.web.dto.CultivoResponse;
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
 * Controlador REST que expone los endpoints del catálogo de cultivos.
 *
 * <p>Base URL: {@code /api/territorial/cultivos}. Todos los métodos requieren
 * autenticación JWT. Las operaciones de escritura están restringidas a los roles
 * ADMINISTRADOR y PROPIETARIO.</p>
 */

@RestController
@RequestMapping("/cultivos")
@RequiredArgsConstructor
@Tag(name = "Cultivos", description = "CRUD de cultivos y asociaciÃ³n con plagas")
public class CultivoController {

    private final GestionarCultivoUseCase gestionarCultivo;
    private final TerritorialWebMapper mapper;

    @GetMapping
    @Operation(summary = "Listar cultivos")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<CultivoResponse>> listar(
            @RequestParam(required = false) Long predioId,
            @RequestParam(required = false) Boolean enTemporada) {
        List<Cultivo> cultivos;
        if (predioId != null) {
            cultivos = gestionarCultivo.listarPorPredio(predioId);
        } else if (Boolean.TRUE.equals(enTemporada)) {
            cultivos = gestionarCultivo.listarEnTemporada();
        } else {
            cultivos = gestionarCultivo.listarTodos();
        }
        return ResponseEntity.ok(mapper.toCultivoResponseList(cultivos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cultivo por ID")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CultivoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarCultivo.obtenerPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Crear cultivo")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<CultivoResponse> crear(@Valid @RequestBody CultivoRequest request) {
        Cultivo creado = gestionarCultivo.crear(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/territorial/cultivos/" + creado.getId()))
                .body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cultivo")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<CultivoResponse> actualizar(
            @PathVariable Long id, @Valid @RequestBody CultivoRequest request) {
        return ResponseEntity.ok(mapper.toResponse(gestionarCultivo.actualizar(id, mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cultivo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarCultivo.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activar cultivo")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<CultivoResponse> activar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarCultivo.activar(id)));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar cultivo")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<CultivoResponse> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarCultivo.desactivar(id)));
    }

    @PostMapping("/{cultivoId}/plagas/{plagaId}")
    @Operation(summary = "Asociar plaga a cultivo")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<CultivoResponse> asociarPlaga(
            @PathVariable Long cultivoId, @PathVariable Long plagaId) {
        return ResponseEntity.ok(mapper.toResponse(gestionarCultivo.asociarPlaga(cultivoId, plagaId)));
    }

    @DeleteMapping("/{cultivoId}/plagas/{plagaId}")
    @Operation(summary = "Desasociar plaga de cultivo")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<CultivoResponse> desasociarPlaga(
            @PathVariable Long cultivoId, @PathVariable Long plagaId) {
        return ResponseEntity.ok(mapper.toResponse(gestionarCultivo.desasociarPlaga(cultivoId, plagaId)));
    }
}

