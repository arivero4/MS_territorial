package territorial.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/** DTO de salida con datos del departamento y su codigo DANE. */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoResponse {

    private Long id;
    private String nombre;
    private String codigoDane;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
