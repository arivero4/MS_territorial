package territorial.domain.exception;

/**
 * Excepcion de dominio lanzada cuando no se encuentra un/a Municipio en el repositorio.
 *
 * <p>RuntimeException no verificada. Capturada por GlobalExceptionHandler
 * y convertida en respuesta HTTP 404 Not Found.</p>
 */
public class MunicipioNoEncontradoException extends RuntimeException {

    public MunicipioNoEncontradoException(Long id) {
        super("Municipio no encontrado con id: " + id);
    }

    public MunicipioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
