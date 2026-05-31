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

/**
 * Entidad de dominio que representa un Lote de cultivo.
 *
 * <p>Un lote es la unidad operativa mínima de producción. Pertenece a un
 * {@link LugarProduccion} y tiene un {@link Cultivo} asignado. El ciclo
 * de vida del lote se gestiona mediante el enum {@link EstadoLote}.</p>
 *
 * <p>Las plagas <strong>no</strong> se asocian al lote directamente;
 * pertenecen al {@link Cultivo} del lote.</p>
 *
 * <p>Jerarquía: LugarProduccion → <strong>Lote</strong> → Cultivo → Plaga.</p>
 *
 * <p>Tabla Oracle: {@code LOTE} con FKs {@code ID_LUGAR} e {@code ID_CULTIVO}.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lote {

    /** Identificador único. Asignado por la secuencia Oracle {@code SEQ_LOTE}. */
    private Long id;

    /** Número de lote asignado internamente por el productor (ej.: "L-01"). */
    private String numero;

    /** Nombre descriptivo del lote (ej.: "Lote Norte", "Lote Sur Aguacate"). */
    private String nombre;

    /** Área del lote expresada en hectáreas. */
    private Double area;

    /**
     * Estado actual del ciclo de vida del lote.
     * Posibles valores: {@code ACTIVO, INACTIVO, EN_PRODUCCION, COSECHADO, ABANDONADO}.
     * @see EstadoLote
     */
    private EstadoLote estado;

    /** Fecha en que se realizó la siembra en este lote. */
    private LocalDate fechaSiembra;

    /** Fecha estimada de cosecha del cultivo sembrado. */
    private LocalDate fechaCosechaEstimada;

    /** Fecha real en que se realizó la cosecha. Se asigna automáticamente en {@link #cosechar()}. */
    private LocalDate fechaCosechaReal;

    /**
     * Coordenadas geográficas del lote.
     * Encapsuladas en el value object {@link Coordenadas}.
     */
    private Coordenadas coordenadas;

    /**
     * Cultivo sembrado en este lote. Relación N:1; FK {@code ID_CULTIVO}.
     * Contiene nombre común, nombre científico y plagas asociadas.
     */
    private Cultivo cultivo;

    /**
     * ID del lugar de producción al que pertenece este lote (FK directa).
     * Se usa cuando solo se necesita el ID sin cargar el objeto completo.
     */
    private Long idLugar;

    /**
     * Lugar de producción al que pertenece este lote. Objeto completo cargado por JPA.
     * FK en tabla: {@code ID_LUGAR}.
     */
    private LugarProduccion lugarProduccion;

    /** Fecha de creación del registro. */
    private LocalDateTime fechaCreacion;

    /** Fecha de última modificación. */
    private LocalDateTime fechaActualizacion;

    /**
     * Indica si el lote está operativo (ni inactivo ni abandonado).
     *
     * @return {@code true} para estados {@code ACTIVO} y {@code EN_PRODUCCION}.
     */
    public boolean estaActivo() {
        return estado != null && estado != EstadoLote.INACTIVO && estado != EstadoLote.ABANDONADO;
    }

    /**
     * Indica si el lote tiene un cultivo en producción activa.
     *
     * @return {@code true} si el estado es {@code EN_PRODUCCION}.
     */
    public boolean estaEnProduccion() {
        return EstadoLote.EN_PRODUCCION.equals(estado);
    }

    /**
     * Indica si el lote ya fue cosechado.
     *
     * @return {@code true} si el estado es {@code COSECHADO}.
     */
    public boolean fueCosechado() {
        return EstadoLote.COSECHADO.equals(estado);
    }

    /**
     * Devuelve el ID del cultivo asignado a este lote, o {@code null} si no tiene.
     *
     * @return ID del cultivo o {@code null}.
     */
    public Long getCultivoId() {
        return cultivo != null ? cultivo.getId() : null;
    }

    /**
     * Cambia el estado a {@code EN_PRODUCCION} y registra la modificación.
     * Se invoca cuando el productor inicia el ciclo de cultivo en el lote.
     */
    public void iniciarProduccion() {
        this.estado = EstadoLote.EN_PRODUCCION;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Cambia el estado a {@code COSECHADO}, registra la fecha real de cosecha
     * y actualiza la fecha de modificación.
     */
    public void cosechar() {
        this.estado = EstadoLote.COSECHADO;
        this.fechaCosechaReal = LocalDate.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Cambia el estado a {@code ABANDONADO} y registra la modificación.
     * Un lote abandonado no puede recibir nuevas inspecciones.
     */
    public void abandonar() {
        this.estado = EstadoLote.ABANDONADO;
        this.fechaActualizacion = LocalDateTime.now();
    }
}
