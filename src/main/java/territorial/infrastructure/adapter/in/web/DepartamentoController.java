package territorial.infrastructure.adapter.in.web;

import territorial.application.port.in.ConsultarTerritorialUseCase;
import territorial.application.port.in.GestionarDepartamentoUseCase;
import territorial.domain.model.Departamento;
import territorial.infrastructure.adapter.in.web.dto.DepartamentoRequest;
import territorial.infrastructure.adapter.in.web.dto.DepartamentoResponse;
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
 * Controlador REST para la gestión de departamentos de Colombia.
 *
 * <p>Base URL: {@code /api/territorial/departamentos}.</p>
 */

@RestController
@RequestMapping("/departamentos")
@RequiredArgsConstructor
@Tag(name = "Departamentos", description = "Gestión de departamentos colombianos")
public class DepartamentoController {

    private final GestionarDepartamentoUseCase gestionarDepartamento;
    private final ConsultarTerritorialUseCase consultarUseCase;
    private final TerritorialWebMapper mapper;

    @GetMapping
    @Operation(summary = "Listar todos los departamentos activos")
    public ResponseEntity<List<DepartamentoResponse>> listar() {
        return ResponseEntity.ok(mapper.toDepartamentoResponseList(gestionarDepartamento.listarActivos()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener departamento por ID")
    public ResponseEntity<DepartamentoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarDepartamento.obtenerPorId(id)));
    }

    @GetMapping("/{id}/municipios")
    @Operation(summary = "Listar municipios de un departamento")
    public ResponseEntity<List<?>> listarMunicipios(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toMunicipioResponseList(consultarUseCase.listarMunicipiosPorDepartamento(id)));
    }

    @PostMapping
    @Operation(summary = "Crear departamento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartamentoResponse> crear(@Valid @RequestBody DepartamentoRequest request) {
        Departamento creado = gestionarDepartamento.crear(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/territorial/departamentos/" + creado.getId()))
                .body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar departamento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartamentoResponse> actualizar(
            @PathVariable Long id, @Valid @RequestBody DepartamentoRequest request) {
        return ResponseEntity.ok(mapper.toResponse(gestionarDepartamento.actualizar(id, mapper.toDomain(request))));
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activar departamento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartamentoResponse> activar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarDepartamento.activar(id)));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar departamento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartamentoResponse> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarDepartamento.desactivar(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar departamento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarDepartamento.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
