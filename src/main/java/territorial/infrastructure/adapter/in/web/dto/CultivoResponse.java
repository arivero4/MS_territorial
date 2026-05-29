package territorial.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CultivoResponse {

    private Long id;
    private String nombreVariedad;
    private String nombreCientifico;
    private String nombreComun;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaCosecha;
    private Boolean activo;
    private Boolean enTemporada;
    private Long predioId;
    private String predioNombre;
    private Integer totalLotes;
    private List<PlagaResponse> plagas;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
