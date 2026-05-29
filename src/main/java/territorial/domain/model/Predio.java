package territorial.domain.model;

import territorial.domain.valueobject.Coordenadas;
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
public class Predio {

    private Long id;
    private String nombre;
    private String numeroPredial;
    private String matriculaInmobiliaria;
    private Double area;
    private String vereda;
    private String descripcion;
    private Coordenadas coordenadas;
    private LugarProduccion lugarProduccion;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    @Builder.Default
    private List<Cultivo> cultivos = new ArrayList<>();

    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    public Long getLugarProduccionId() {
        return lugarProduccion != null ? lugarProduccion.getId() : null;
    }

    public boolean tieneMatriculaInmobiliaria() {
        return matriculaInmobiliaria != null && !matriculaInmobiliaria.isBlank();
    }

    public void activar() {
        this.activo = true;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void desactivar() {
        this.activo = false;
        this.fechaActualizacion = LocalDateTime.now();
    }
}
