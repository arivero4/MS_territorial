package territorial.infrastructure.adapter.in.web;

import territorial.application.port.in.GestionarLoteUseCase;
import territorial.domain.enums.EstadoLote;
import territorial.domain.model.Lote;
import territorial.infrastructure.adapter.in.web.dto.LoteRequest;
import territorial.infrastructure.adapter.in.web.dto.LoteResponse;
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

@RestController
@RequestMapping("/lotes")
@RequiredArgsConstructor
@Tag(name = "Lotes", description = "Gestión de lotes de cultivo y su ciclo de producción")
public class LoteController {

    private final GestionarLoteUseCase gestionarLote;
    private final TerritorialWebMapper mapper;

    @GetMapping
    @Operation(summary = "Listar lotes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LoteResponse>> listar(
            @RequestParam(required = false) Long cultivoId,
            @RequestParam(required = false) EstadoLote estado) {
        List<Lote> lotes;
        if (cultivoId != null) {
            lotes = gestionarLote.listarPorCultivo(cultivoId);
        } else if (estado != null) {
            lotes = gestionarLote.listarPorEstado(estado);
        } else {
            lotes = gestionarLote.listarTodos();
        }
        return ResponseEntity.ok(mapper.toLoteResponseList(lotes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener lote por ID")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LoteResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLote.obtenerPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Crear lote")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LoteResponse> crear(@Valid @RequestBody LoteRequest request) {
        Lote creado = gestionarLote.crear(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/territorial/lotes/" + creado.getId()))
                .body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar lote")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LoteResponse> actualizar(
            @PathVariable Long id, @Valid @RequestBody LoteRequest request) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLote.actualizar(id, mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarLote.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/iniciar-produccion")
    @Operation(summary = "Iniciar producción del lote")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LoteResponse> iniciarProduccion(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLote.iniciarProduccion(id)));
    }

    @PatchMapping("/{id}/cosechar")
    @Operation(summary = "Registrar cosecha del lote")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LoteResponse> cosechar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLote.cosechar(id)));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado del lote")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LoteResponse> cambiarEstado(
            @PathVariable Long id, @RequestParam EstadoLote estado) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLote.cambiarEstado(id, estado)));
    }

    @PostMapping("/{loteId}/plagas/{plagaId}")
    @Operation(summary = "Asociar plaga a lote")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LoteResponse> asociarPlaga(
            @PathVariable Long loteId, @PathVariable Long plagaId) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLote.asociarPlaga(loteId, plagaId)));
    }

    @DeleteMapping("/{loteId}/plagas/{plagaId}")
    @Operation(summary = "Desasociar plaga de lote")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<LoteResponse> desasociarPlaga(
            @PathVariable Long loteId, @PathVariable Long plagaId) {
        return ResponseEntity.ok(mapper.toResponse(gestionarLote.desasociarPlaga(loteId, plagaId)));
    }
}
