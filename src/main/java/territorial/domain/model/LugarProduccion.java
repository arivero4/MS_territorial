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
public class LugarProduccion {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double area;
    private String vereda;
    private Coordenadas coordenadas;
    private Municipio municipio;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    @Builder.Default
    private List<Predio> predios = new ArrayList<>();

    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    public Long getMunicipioId() {
        return municipio != null ? municipio.getId() : null;
    }

    public boolean tieneCoordenadasValidas() {
        return coordenadas != null
                && coordenadas.getLatitud() != null
                && coordenadas.getLongitud() != null;
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
