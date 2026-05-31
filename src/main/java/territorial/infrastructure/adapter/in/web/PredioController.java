package territorial.infrastructure.adapter.in.web;

import territorial.application.port.in.GestionarPredioUseCase;
import territorial.domain.model.Predio;
import territorial.infrastructure.adapter.in.web.dto.PredioRequest;
import territorial.infrastructure.adapter.in.web.dto.PredioResponse;
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
 * Controlador REST que expone los endpoints de gestión de predios rurales.
 *
 * <p>Base URL: {@code /api/territorial/predios}. Devuelve la cadena de ubicación
 * completa: municipio, departamento y lugar de producción.</p>
 */

@RestController
@RequestMapping("/predios")
@RequiredArgsConstructor
@Tag(name = "Predios", description = "CRUD de predios rurales")
public class PredioController {

    private final GestionarPredioUseCase gestionarPredio;
    private final TerritorialWebMapper mapper;

    @GetMapping
    @Operation(summary = "Listar predios")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<PredioResponse>> listar(
            @RequestParam(required = false) Long lugarProduccionId) {
        List<Predio> predios = lugarProduccionId != null
                ? gestionarPredio.listarPorLugarProduccion(lugarProduccionId)
                : gestionarPredio.listarTodos();
        return ResponseEntity.ok(mapper.toPredioResponseList(predios));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener predio por ID")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PredioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarPredio.obtenerPorId(id)));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar predio por nÃºmero predial")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<PredioResponse>> buscarPorNumeroPredial(
            @RequestParam String numeroPredial) {
        return ResponseEntity.ok(mapper.toPredioResponseList(
                gestionarPredio.buscarPorNumeroPredial(numeroPredial)));
    }

    @PostMapping
    @Operation(summary = "Crear predio")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<PredioResponse> crear(@Valid @RequestBody PredioRequest request) {
        Predio creado = gestionarPredio.crear(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/territorial/predios/" + creado.getId()))
                .body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar predio")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<PredioResponse> actualizar(
            @PathVariable Long id, @Valid @RequestBody PredioRequest request) {
        return ResponseEntity.ok(mapper.toResponse(gestionarPredio.actualizar(id, mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar predio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarPredio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activar predio")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<PredioResponse> activar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarPredio.activar(id)));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar predio")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity<PredioResponse> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarPredio.desactivar(id)));
    }
}

