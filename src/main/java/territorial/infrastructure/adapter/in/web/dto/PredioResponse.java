package territorial.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/** DTO de salida con predio: municipio, departamento y lugar de produccion. */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredioResponse {

    private Long id;
    private String nombre;
    private String numeroPredial;
    private String matriculaInmobiliaria;
    private Double area;
    private String vereda;
    private String descripcion;
    private Double latitud;
    private Double longitud;
    private Double altitud;
    private Boolean activo;
    private Long lugarProduccionId;
    private String lugarProduccionNombre;
    private Long municipioId;
    private String municipioNombre;
    private Long departamentoId;
    private String departamentoNombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
