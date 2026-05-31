package territorial.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/** DTO de salida con los datos del lugar de produccion. */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LugarResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double area;
    private String vereda;
    private Double latitud;
    private Double longitud;
    private Double altitud;
    private Boolean activo;
    // municipioId/departamentoId eliminados: LugarProduccion NO tiene municipio directo
    // La cadena es: Municipio → Predio → LugarProduccion → Lote
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
