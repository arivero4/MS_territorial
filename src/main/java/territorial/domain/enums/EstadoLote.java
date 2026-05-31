package territorial.domain.enums;

/**
 * Enumeración que representa el ciclo de vida de un Lote de cultivo.
 *
 * <p>Los estados forman la siguiente progresión típica:</p>
 * <pre>
 *   ACTIVO → EN_PRODUCCION → COSECHADO
 *                         ↘ ABANDONADO
 *   INACTIVO (estado de pausa o mantenimiento)
 * </pre>
 *
 * <p>Cada constante incluye una etiqueta legible ({@link #getEtiqueta()}) para mostrar
 * en el frontend y una descripción ({@link #getDescripcion()}) para documentación.</p>
 */
public enum EstadoLote {

    /** Lote disponible y preparado, aún sin cultivo activo. */
    ACTIVO("Activo", "El lote está activo y preparado"),

    /** Lote temporalmente deshabilitado por mantenimiento u otras razones. */
    INACTIVO("Inactivo", "El lote está inactivo"),

    /** Lote con cultivo sembrado y en pleno período de producción. */
    EN_PRODUCCION("En producción", "El lote tiene cultivo en curso"),

    /** Lote cuyo ciclo de producción finalizó con cosecha exitosa. Estado terminal. */
    COSECHADO("Cosechado", "El lote fue cosechado exitosamente"),

    /** Lote dado de baja definitivamente. No puede recibir nuevas siembras. Estado terminal. */
    ABANDONADO("Abandonado", "El lote fue abandonado");

    /** Etiqueta para mostrar en la interfaz de usuario (ej.: "En producción"). */
    private final String etiqueta;

    /** Descripción completa del estado para documentación y tooltips. */
    private final String descripcion;

    EstadoLote(String etiqueta, String descripcion) {
        this.etiqueta = etiqueta;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la etiqueta legible del estado para mostrar en el frontend.
     *
     * @return cadena de texto legible (ej.: "En producción").
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Devuelve la descripción detallada del estado.
     *
     * @return descripción completa del estado.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Indica si el lote está en un estado operativo (puede recibir inspecciones).
     *
     * @return {@code true} para {@code ACTIVO} y {@code EN_PRODUCCION}.
     */
    public boolean esOperativo() {
        return this == ACTIVO || this == EN_PRODUCCION;
    }
}
