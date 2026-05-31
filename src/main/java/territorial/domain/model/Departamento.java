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
 * Entidad de dominio que representa un Departamento de Colombia.
 *
 * <p>Es el nivel más alto de la jerarquía territorial de TerraIca.
 * Agrupa municipios y se identifica con su código DANE de 2 dígitos.</p>
 *
 * <p>Jerarquía: <strong>Departamento</strong> → Municipio → Predio
 * → LugarProduccion → Lote → Cultivo → Plaga.</p>
 *
 * <p>Tabla Oracle: {@code DEPARTAMENTO} (esquema {@code territorial}).</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Departamento {

    /** Identificador único. Asignado por la secuencia Oracle {@code SEQ_DEPARTAMENTO}. */
    private Long id;

    /** Nombre oficial del departamento (ej.: "Antioquia", "Cundinamarca"). */
    private String nombre;

    /**
     * Código DANE de 2 dígitos (ej.: "05" para Antioquia).
     * Encapsulado en {@link CodigoDane} para garantizar formato válido.
     */
    private CodigoDane codigoDane;

    /** {@code true} si el departamento está habilitado en el sistema. */
    private Boolean activo;

    /** Fecha de creación del registro en el sistema. */
    private LocalDateTime fechaCreacion;

    /** Fecha de la última modificación del registro. */
    private LocalDateTime fechaActualizacion;

    /**
     * Municipios que pertenecen a este departamento (relación 1:N).
     * Inicializado como lista vacía para evitar NullPointerException.
     */
    @Builder.Default
    private List<Municipio> municipios = new ArrayList<>();

    /**
     * Indica si el departamento está habilitado en el sistema.
     *
     * @return {@code true} sólo cuando {@code activo} es explícitamente {@code Boolean.TRUE}.
     */
    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Habilita el departamento y registra la fecha de modificación.
     */
    public void activar() {
        this.activo = true;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Deshabilita el departamento y registra la fecha de modificación.
     * Un departamento inactivo no aparece en los selectores del frontend.
     */
    public void desactivar() {
        this.activo = false;
        this.fechaActualizacion = LocalDateTime.now();
    }
}
