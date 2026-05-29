package territorial.domain.enums;

public enum EstadoLote {

    ACTIVO("Activo", "El lote está activo y preparado"),
    INACTIVO("Inactivo", "El lote está inactivo"),
    EN_PRODUCCION("En producción", "El lote tiene cultivo en curso"),
    COSECHADO("Cosechado", "El lote fue cosechado exitosamente"),
    ABANDONADO("Abandonado", "El lote fue abandonado");

    private final String etiqueta;
    private final String descripcion;

    EstadoLote(String etiqueta, String descripcion) {
        this.etiqueta = etiqueta;
        this.descripcion = descripcion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean esOperativo() {
        return this == ACTIVO || this == EN_PRODUCCION;
    }
}
