package territorial.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Long municipioId;
    private String municipioNombre;
    private Long departamentoId;
    private String departamentoNombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
