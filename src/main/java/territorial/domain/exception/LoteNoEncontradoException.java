package territorial.domain.exception;

/**
 * Excepcion de dominio lanzada cuando no se encuentra un/a Lote en el repositorio.
 *
 * <p>RuntimeException no verificada. Capturada por GlobalExceptionHandler
 * y convertida en respuesta HTTP 404 Not Found.</p>
 */
public class LoteNoEncontradoException extends RuntimeException {

    public LoteNoEncontradoException(Long id) {
        super("Lote no encontrado con id: " + id);
    }

    public LoteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
