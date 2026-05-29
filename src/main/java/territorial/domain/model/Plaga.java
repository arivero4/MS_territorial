package territorial.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plaga {

    private Long id;
    private String nombreCientifico;
    private String nombreComun;
    private String descripcion;
    private String tipo;
    private String nivelRiesgo;
    private String sintomas;
    private String tratamiento;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    public boolean esAltoRiesgo() {
        return "ALTO".equalsIgnoreCase(nivelRiesgo);
    }

    public boolean esMedioRiesgo() {
        return "MEDIO".equalsIgnoreCase(nivelRiesgo);
    }
}
