package territorial.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de dominio que representa un Cultivo hortifrutícola.
 *
 * <p>Catálogo de variedades de cultivos que pueden sembrarse en los {@link Lote}s.
 * Cada cultivo puede tener varias plagas fitosanitarias asociadas.</p>
 *
 * <p><strong>No</strong> tiene referencia directa a {@link Predio}; el cultivo
 * se accede navegando: LugarProduccion → Lote → Cultivo.</p>
 *
 * <p>Jerarquía: Lote → <strong>Cultivo</strong> → Plaga.</p>
 *
 * <p>Tabla Oracle: {@code CULTIVO}. Su ID es referenciado por {@code LOTE.ID_CULTIVO}
 * y {@code PLAGA.ID_CULTIVO}.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cultivo {

    /** Identificador único. Asignado por la secuencia Oracle {@code SEQ_CULTIVO}. */
    private Long id;

    /** Nombre de la variedad específica (ej.: "Mango Tommy Atkins", "Banano Cavendish"). */
    private String nombreVariedad;

    /** Nombre científico del cultivo en latín (ej.: "Mangifera indica"). */
    private String nombreCientifico;

    /** Nombre común usado en campo (ej.: "Mango", "Banano", "Café"). */
    private String nombreComun;

    /** Descripción agronómica del cultivo (período vegetativo, condiciones ideales, etc.). */
    private String descripcion;

    /** Fecha de inicio del período de producción de esta variedad. */
    private LocalDate fechaInicio;

    /** Fecha estimada en que termina el período productivo (cosecha). */
    private LocalDate fechaEstimadaCosecha;

    /** {@code true} si el cultivo está disponible para ser asignado a nuevos lotes. */
    private Boolean activo;

    /** Fecha de creación del registro. */
    private LocalDateTime fechaCreacion;

    /** Fecha de última modificación. */
    private LocalDateTime fechaActualizacion;

    /**
     * Lotes que tienen este cultivo sembrado (relación 1:N inversa).
     * Inicializado vacío para evitar NullPointerException.
     */
    @Builder.Default
    private List<Lote> lotes = new ArrayList<>();

    /**
     * Plagas fitosanitarias que afectan este cultivo (relación 1:N).
     * Fuente del catálogo de plagas que el AT selecciona durante el conteo.
     */
    @Builder.Default
    private List<Plaga> plagas = new ArrayList<>();

    /**
     * Indica si el cultivo está disponible en el sistema.
     *
     * @return {@code true} cuando {@code activo} es {@code Boolean.TRUE}.
     */
    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Verifica si la fecha actual se encuentra dentro del período productivo del cultivo
     * (entre {@code fechaInicio} y {@code fechaEstimadaCosecha}).
     *
     * @return {@code true} si hoy está dentro del período de producción.
     *         {@code false} si alguna de las fechas es {@code null}.
     */
    public boolean estaEnTemporada() {
        if (fechaInicio == null || fechaEstimadaCosecha == null) return false;
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaEstimadaCosecha);
    }

    /**
     * Devuelve el número de lotes que tienen este cultivo sembrado.
     *
     * @return cantidad de lotes asociados, o 0 si la lista es null.
     */
    public int totalLotes() {
        return lotes != null ? lotes.size() : 0;
    }

    /**
     * Habilita el cultivo y actualiza la fecha de modificación.
     */
    public void activar() {
        this.activo = true;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Deshabilita el cultivo. Un cultivo inactivo no aparece en los selectores
     * al crear nuevos lotes.
     */
    public void desactivar() {
        this.activo = false;
        this.fechaActualizacion = LocalDateTime.now();
    }
}
