package territorial.domain.exception;

/**
 * Excepcion de dominio lanzada cuando no se encuentra un/a Lugar en el repositorio.
 *
 * <p>RuntimeException no verificada. Capturada por GlobalExceptionHandler
 * y convertida en respuesta HTTP 404 Not Found.</p>
 */
public class LugarNoEncontradoException extends RuntimeException {

    public LugarNoEncontradoException(Long id) {
        super("Lugar de producción no encontrado con id: " + id);
    }

    public LugarNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
