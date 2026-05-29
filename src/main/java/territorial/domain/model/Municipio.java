package territorial.domain.model;

import territorial.domain.valueobject.CodigoDane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Municipio {

    private Long id;
    private String nombre;
    private CodigoDane codigoDane;
    private Departamento departamento;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    @Builder.Default
    private List<LugarProduccion> lugaresProduccion = new ArrayList<>();

    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    public Long getDepartamentoId() {
        return departamento != null ? departamento.getId() : null;
    }
}
