package territorial.infrastructure.adapter.in.web;

import territorial.application.port.in.GestionarPlagaUseCase;
import territorial.domain.model.Plaga;
import territorial.infrastructure.adapter.in.web.dto.PlagaRequest;
import territorial.infrastructure.adapter.in.web.dto.PlagaResponse;
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
@RequestMapping("/plagas")
@RequiredArgsConstructor
@Tag(name = "Plagas", description = "CatÃ¡logo de plagas agrÃ­colas")
public class PlagaController {

    private final GestionarPlagaUseCase gestionarPlaga;
    private final TerritorialWebMapper mapper;

    @GetMapping
    @Operation(summary = "Listar plagas")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<PlagaResponse>> listar(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String nivelRiesgo,
            @RequestParam(required = false) String nombre) {
        List<Plaga> plagas;
        if (tipo != null) {
            plagas = gestionarPlaga.listarPorTipo(tipo);
        } else if (nivelRiesgo != null) {
            plagas = gestionarPlaga.listarPorNivelRiesgo(nivelRiesgo);
        } else if (nombre != null) {
            plagas = gestionarPlaga.buscarPorNombre(nombre);
        } else {
            plagas = gestionarPlaga.listarTodas();
        }
        return ResponseEntity.ok(mapper.toPlagaResponseList(plagas));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener plaga por ID")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PlagaResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarPlaga.obtenerPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Crear plaga en el catÃ¡logo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlagaResponse> crear(@Valid @RequestBody PlagaRequest request) {
        Plaga creada = gestionarPlaga.crear(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/territorial/plagas/" + creada.getId()))
                .body(mapper.toResponse(creada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar plaga")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlagaResponse> actualizar(
            @PathVariable Long id, @Valid @RequestBody PlagaRequest request) {
        return ResponseEntity.ok(mapper.toResponse(gestionarPlaga.actualizar(id, mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar plaga del catÃ¡logo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarPlaga.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

