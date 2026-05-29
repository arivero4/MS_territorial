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
public class MunicipioResponse {

    private Long id;
    private String nombre;
    private String codigoDane;
    private Boolean activo;
    private Long departamentoId;
    private String departamentoNombre;
    private String departamentoCodigoDane;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
