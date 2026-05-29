package territorial.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cultivo {

    private Long id;
    private String nombreVariedad;
    private String nombreCientifico;
    private String nombreComun;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaCosecha;
    private Predio predio;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    @Builder.Default
    private List<Lote> lotes = new ArrayList<>();

    @Builder.Default
    private List<Plaga> plagas = new ArrayList<>();

    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    public Long getPredioId() {
        return predio != null ? predio.getId() : null;
    }

    public boolean estaEnTemporada() {
        if (fechaInicio == null || fechaEstimadaCosecha == null) return false;
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaEstimadaCosecha);
    }

    public int totalLotes() {
        return lotes != null ? lotes.size() : 0;
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
