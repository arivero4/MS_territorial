package territorial.domain.model;

import territorial.domain.enums.EstadoLote;
import territorial.domain.valueobject.Coordenadas;
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
public class Lote {

    private Long id;
    private String numero;
    private String nombre;
    private Double area;
    private EstadoLote estado;
    private LocalDate fechaSiembra;
    private LocalDate fechaCosechaEstimada;
    private LocalDate fechaCosechaReal;
    private Coordenadas coordenadas;
    private Cultivo cultivo;
    private Long idLugar;         // Diccionario: lote.id_lugar FK directa
    private LugarProduccion lugarProduccion;  // Para el mapeo completo
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    @Builder.Default
    private List<Plaga> plagas = new ArrayList<>();

    public boolean estaActivo() {
        return estado != null && estado != EstadoLote.INACTIVO && estado != EstadoLote.ABANDONADO;
    }

    public boolean estaEnProduccion() {
        return EstadoLote.EN_PRODUCCION.equals(estado);
    }

    public boolean fueCosechado() {
        return EstadoLote.COSECHADO.equals(estado);
    }

    public Long getCultivoId() {
        return cultivo != null ? cultivo.getId() : null;
    }

    public void iniciarProduccion() {
        this.estado = EstadoLote.EN_PRODUCCION;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void cosechar() {
        this.estado = EstadoLote.COSECHADO;
        this.fechaCosechaReal = LocalDate.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void abandonar() {
        this.estado = EstadoLote.ABANDONADO;
        this.fechaActualizacion = LocalDateTime.now();
    }
}
