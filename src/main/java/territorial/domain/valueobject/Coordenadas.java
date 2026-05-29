package territorial.domain.valueobject;

import java.util.Objects;

public final class Coordenadas {

    private final Double latitud;
    private final Double longitud;
    private final Double altitud;

    private Coordenadas(Double latitud, Double longitud, Double altitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
    }

    public static Coordenadas de(Double latitud, Double longitud) {
        validarLatitud(latitud);
        validarLongitud(longitud);
        return new Coordenadas(latitud, longitud, null);
    }

    public static Coordenadas de(Double latitud, Double longitud, Double altitud) {
        validarLatitud(latitud);
        validarLongitud(longitud);
        return new Coordenadas(latitud, longitud, altitud);
    }

    private static void validarLatitud(Double latitud) {
        if (latitud == null) throw new IllegalArgumentException("La latitud no puede ser nula");
        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException("Latitud inválida: debe estar entre -90 y 90. Valor: " + latitud);
        }
    }

    private static void validarLongitud(Double longitud) {
        if (longitud == null) throw new IllegalArgumentException("La longitud no puede ser nula");
        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("Longitud inválida: debe estar entre -180 y 180. Valor: " + longitud);
        }
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Double getAltitud() {
        return altitud;
    }

    public boolean tieneAltitud() {
        return altitud != null;
    }

    public String toWkt() {
        return String.format("POINT(%s %s)", longitud, latitud);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordenadas that = (Coordenadas) o;
        return Objects.equals(latitud, that.latitud)
                && Objects.equals(longitud, that.longitud)
                && Objects.equals(altitud, that.altitud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitud, longitud, altitud);
    }

    @Override
    public String toString() {
        return String.format("Coordenadas{lat=%.6f, lon=%.6f%s}",
                latitud, longitud,
                altitud != null ? ", alt=" + altitud : "");
    }
}
