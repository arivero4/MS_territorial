package territorial.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa una Plaga fitosanitaria.
 *
 * <p>Catálogo de organismos (insectos, hongos, bacterias, etc.) que afectan
 * los cultivos hortifrutícolas. Cada plaga pertenece a un {@link Cultivo}
 * específico mediante la FK {@code ID_CULTIVO}.</p>
 *
 * <p>Las plagas del catálogo son las que el Asistente Técnico puede seleccionar
 * durante el conteo en vivo de una inspección fitosanitaria.</p>
 *
 * <p>El nombre científico se usa para obtener imágenes desde la API de Wikipedia
 * en el frontend ({@code PlagaImages.getImageUrl(nombreCientifico)}).</p>
 *
 * <p>Jerarquía: Cultivo → <strong>Plaga</strong>.</p>
 *
 * <p>Tabla Oracle: {@code PLAGA} con FK {@code ID_CULTIVO}.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plaga {

    /** Identificador único. Asignado por la secuencia Oracle correspondiente. */
    private Long id;

    /**
     * Nombre científico en latín (ej.: "Bemisia tabaci", "Tetranychus urticae").
     * Usado por el frontend para cargar la imagen representativa desde Wikipedia.
     */
    private String nombreCientifico;

    /** Nombre común de la plaga (ej.: "Mosca blanca", "Ácaro rojo"). */
    private String nombreComun;

    /** Descripción del comportamiento y daños que causa la plaga. */
    private String descripcion;

    /**
     * Tipo de organismo fitosanitario.
     * Valores esperados: {@code INSECTO}, {@code HONGO}, {@code BACTERIA},
     * {@code VIRUS}, {@code ACARO}, {@code NEMATODO}.
     * Usado por el frontend para seleccionar el icono de fallback.
     */
    private String tipo;

    /**
     * Nivel de riesgo fitosanitario de la plaga.
     * Valores: {@code BAJO}, {@code MEDIO}, {@code ALTO}.
     * Verificable con {@link #esAltoRiesgo()} y {@link #esMedioRiesgo()}.
     */
    private String nivelRiesgo;

    /** Descripción de los síntomas visibles que produce la plaga en el cultivo. */
    private String sintomas;

    /** Medidas de control y tratamiento recomendadas para esta plaga. */
    private String tratamiento;

    /** {@code true} si la plaga está activa en el catálogo del sistema. */
    private Boolean activo;

    /**
     * ID del cultivo al que afecta esta plaga (FK directa {@code ID_CULTIVO}).
     * Permite recuperar el cultivo sin cargar el objeto completo.
     */
    private Long idCultivo;

    /** Fecha de creación del registro. */
    private LocalDateTime fechaCreacion;

    /** Fecha de última modificación. */
    private LocalDateTime fechaActualizacion;

    /**
     * Indica si la plaga está activa en el catálogo.
     *
     * @return {@code true} cuando {@code activo} es {@code Boolean.TRUE}.
     */
    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Indica si la plaga tiene nivel de riesgo ALTO (>= 30% incidencia típica).
     *
     * @return {@code true} si {@code nivelRiesgo} es "ALTO" (sin distinción de mayúsculas).
     */
    public boolean esAltoRiesgo() {
        return "ALTO".equalsIgnoreCase(nivelRiesgo);
    }

    /**
     * Indica si la plaga tiene nivel de riesgo MEDIO (10–29% incidencia típica).
     *
     * @return {@code true} si {@code nivelRiesgo} es "MEDIO" (sin distinción de mayúsculas).
     */
    public boolean esMedioRiesgo() {
        return "MEDIO".equalsIgnoreCase(nivelRiesgo);
    }
}
