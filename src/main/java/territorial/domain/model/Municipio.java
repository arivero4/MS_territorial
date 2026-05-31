package territorial.domain.model;

import territorial.domain.valueobject.CodigoDane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de dominio que representa un Municipio de Colombia.
 *
 * <p>Segundo nivel de la jerarquía territorial. Pertenece a un
 * {@link Departamento} y agrupa los {@link Predio}s donde se ubican
 * los cultivos hortifrutícolas inspeccionados.</p>
 *
 * <p>Jerarquía: Departamento → <strong>Municipio</strong> → Predio
 * → LugarProduccion → Lote → Cultivo → Plaga.</p>
 *
 * <p>Tabla Oracle: {@code MUNICIPIO} (FK {@code ID_DEPARTAMENTO}).</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Municipio {

    /** Identificador único. Asignado por la secuencia Oracle {@code SEQ_MUNICIPIO}. */
    private Long id;

    /** Nombre oficial del municipio (ej.: "Medellín", "Palmira"). */
    private String nombre;

    /**
     * Código DANE de 5 dígitos del municipio (ej.: "05001" para Medellín).
     * Validado por el value object {@link CodigoDane}.
     */
    private CodigoDane codigoDane;

    /**
     * Departamento al que pertenece este municipio.
     * Relación N:1; el municipio tiene FK {@code ID_DEPARTAMENTO}.
     */
    private Departamento departamento;

    /** {@code true} si el municipio está habilitado en el sistema. */
    private Boolean activo;

    /** Fecha de creación del registro. */
    private LocalDateTime fechaCreacion;

    /** Fecha de última modificación. */
    private LocalDateTime fechaActualizacion;

    /**
     * Lugares de producción asociados a este municipio (referenciado desde Predio).
     * Lista de conveniencia; inicializada vacía para evitar NullPointerException.
     */
    @Builder.Default
    private List<LugarProduccion> lugaresProduccion = new ArrayList<>();

    /**
     * Indica si el municipio está habilitado.
     *
     * @return {@code true} cuando {@code activo} es explícitamente {@code Boolean.TRUE}.
     */
    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Devuelve el ID del departamento asociado, o {@code null} si no tiene.
     *
     * @return ID del departamento o {@code null}.
     */
    public Long getDepartamentoId() {
        return departamento != null ? departamento.getId() : null;
    }
}
