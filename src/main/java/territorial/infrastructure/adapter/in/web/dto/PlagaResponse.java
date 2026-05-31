package territorial.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/** DTO de salida: nombre cientifico, tipo, nivel de riesgo y cultivo afectado. */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlagaResponse {

    private Long id;
    private String nombreCientifico;
    private String nombreComun;
    private String descripcion;
    private String tipo;
    private String nivelRiesgo;
    private String sintomas;
    private String tratamiento;
    private Boolean activo;
    // Diccionario: plaga.id_cultivo FK directa
    private Long idCultivo;
    private String cultivoNombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
