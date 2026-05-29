package territorial.infrastructure.adapter.in.web.dto;

import territorial.domain.enums.EstadoLote;
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
public class LoteResponse {

    private Long id;
    private String numero;
    private String nombre;
    private Double area;
    private EstadoLote estado;
    private String estadoEtiqueta;
    private LocalDate fechaSiembra;
    private LocalDate fechaCosechaEstimada;
    private LocalDate fechaCosechaReal;
    private Double latitud;
    private Double longitud;
    private Double altitud;
    private Long cultivoId;
    private String cultivoNombre;
    private List<PlagaResponse> plagas;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
