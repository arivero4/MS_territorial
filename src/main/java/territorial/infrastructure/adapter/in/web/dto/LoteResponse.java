package territorial.infrastructure.adapter.in.web.dto;

import territorial.domain.enums.EstadoLote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/** DTO de salida con el lote, cultivo, lugar de produccion y estado. */

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
    private Long idLugar;
    private String lugarNombre;
    // plagas eliminadas de LoteResponse: las plagas pertenecen al Cultivo, no al Lote
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
