package territorial.domain.exception;

/**
 * Excepcion de dominio lanzada cuando no se encuentra un/a Predio en el repositorio.
 *
 * <p>RuntimeException no verificada. Capturada por GlobalExceptionHandler
 * y convertida en respuesta HTTP 404 Not Found.</p>
 */
public class PredioNoEncontradoException extends RuntimeException {

    public PredioNoEncontradoException(Long id) {
        super("Predio no encontrado con id: " + id);
    }

    public PredioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
