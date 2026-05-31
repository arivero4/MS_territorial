package territorial.domain.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object inmutable que representa un Código DANE.
 *
 * <p>El DANE (Departamento Administrativo Nacional de Estadística) asigna
 * códigos numéricos a cada división territorial de Colombia:</p>
 * <ul>
 *   <li>Departamento: 2 dígitos (ej.: "05" = Antioquia).</li>
 *   <li>Municipio: 5 dígitos (ej.: "05001" = Medellín).</li>
 * </ul>
 *
 * <p>Inmutable y autovalidado: solo puede crearse con {@link #de(String)} (valida formato)
 * o {@link #deSinValidar(String)} (para datos provenientes de BD ya validados).
 * La igualdad se basa en el valor del código, no en la referencia.</p>
 */
public final class CodigoDane {

    /** Patrón para código de departamento: exactamente 2 dígitos. */
    private static final Pattern PATRON_DEPARTAMENTO = Pattern.compile("^\\d{2}$");

    /** Patrón para código de municipio: exactamente 5 dígitos. */
    private static final Pattern PATRON_MUNICIPIO = Pattern.compile("^\\d{5}$");

    /** Valor del código DANE almacenado como String inmutable. */
    private final String codigo;

    /** Constructor privado. Usar los factory methods {@link #de} o {@link #deSinValidar}. */
    private CodigoDane(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Crea un {@code CodigoDane} validando que el formato sea correcto.
     *
     * @param codigo cadena con el código DANE (2 o 5 dígitos).
     * @return instancia validada de {@code CodigoDane}.
     * @throws IllegalArgumentException si el código es nulo, vacío o no tiene 2 ni 5 dígitos.
     */
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

    /**
     * Crea un {@code CodigoDane} sin validar el formato.
     * Usar únicamente para datos provenientes de la base de datos que ya fueron validados.
     *
     * @param codigo cadena con el código DANE.
     * @return instancia de {@code CodigoDane} sin validación.
     */
    public static CodigoDane deSinValidar(String codigo) {
        return new CodigoDane(codigo);
    }

    /**
     * Devuelve la representación en cadena del código DANE.
     *
     * @return código DANE como String (ej.: "05", "05001").
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Indica si este código corresponde a un departamento (2 dígitos).
     *
     * @return {@code true} si el código tiene exactamente 2 dígitos.
     */
    public boolean esDepartamento() {
        return codigo != null && PATRON_DEPARTAMENTO.matcher(codigo).matches();
    }

    /**
     * Indica si este código corresponde a un municipio (5 dígitos).
     *
     * @return {@code true} si el código tiene exactamente 5 dígitos.
     */
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
