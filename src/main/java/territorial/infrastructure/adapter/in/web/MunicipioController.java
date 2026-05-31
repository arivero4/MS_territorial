package territorial.infrastructure.adapter.in.web;

import territorial.application.port.in.ConsultarTerritorialUseCase;
import territorial.application.port.in.GestionarMunicipioUseCase;
import territorial.domain.model.Municipio;
import territorial.infrastructure.adapter.in.web.dto.MunicipioRequest;
import territorial.infrastructure.adapter.in.web.dto.MunicipioResponse;
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
 * Controlador REST para la gestión de municipios.
 *
 * <p>Base URL: {@code /api/territorial/municipios}.</p>
 */

@RestController
@RequestMapping("/municipios")
@RequiredArgsConstructor
@Tag(name = "Municipios", description = "Gestión de municipios colombianos")
public class MunicipioController {

    private final GestionarMunicipioUseCase gestionarMunicipio;
    private final ConsultarTerritorialUseCase consultarUseCase;
    private final TerritorialWebMapper mapper;

    @GetMapping
    @Operation(summary = "Listar todos los municipios activos")
    public ResponseEntity<List<MunicipioResponse>> listar() {
        return ResponseEntity.ok(mapper.toMunicipioResponseList(gestionarMunicipio.listarTodos()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener municipio por ID")
    public ResponseEntity<MunicipioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarMunicipio.obtenerPorId(id)));
    }

    @GetMapping("/{id}/lugares")
    @Operation(summary = "Listar lugares de producción de un municipio")
    public ResponseEntity<List<?>> listarLugares(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toLugarResponseList(consultarUseCase.listarLugaresPorMunicipio(id)));
    }

    @PostMapping
    @Operation(summary = "Crear municipio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MunicipioResponse> crear(@Valid @RequestBody MunicipioRequest request) {
        Municipio creado = gestionarMunicipio.crear(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/territorial/municipios/" + creado.getId()))
                .body(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar municipio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MunicipioResponse> actualizar(
            @PathVariable Long id, @Valid @RequestBody MunicipioRequest request) {
        return ResponseEntity.ok(mapper.toResponse(gestionarMunicipio.actualizar(id, mapper.toDomain(request))));
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activar municipio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MunicipioResponse> activar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarMunicipio.activar(id)));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar municipio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MunicipioResponse> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(gestionarMunicipio.desactivar(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar municipio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarMunicipio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
