package territorial.domain.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

public final class CodigoDane {

    private static final Pattern PATRON_DEPARTAMENTO = Pattern.compile("^\\d{2}$");
    private static final Pattern PATRON_MUNICIPIO = Pattern.compile("^\\d{5}$");

    private final String codigo;

    private CodigoDane(String codigo) {
        this.codigo = codigo;
    }

    public static CodigoDane de(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código DANE no puede ser nulo o vacío");
        }
        String codigoLimpio = codigo.trim();
        if (!PATRON_DEPARTAMENTO.matcher(codigoLimpio).matches()
                && !PATRON_MUNICIPIO.matcher(codigoLimpio).matches()) {
            throw new IllegalArgumentException(
                    "Código DANE inválido: debe tener 2 dígitos (departamento) o 5 dígitos (municipio): " + codigoLimpio);
        }
        return new CodigoDane(codigoLimpio);
    }

    public static CodigoDane deSinValidar(String codigo) {
        return new CodigoDane(codigo);
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean esDepartamento() {
        return codigo != null && PATRON_DEPARTAMENTO.matcher(codigo).matches();
    }

    public boolean esMunicipio() {
        return codigo != null && PATRON_MUNICIPIO.matcher(codigo).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoDane that = (CodigoDane) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
}
