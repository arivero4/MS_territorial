package territorial.domain.exception;

/**
 * Excepcion de dominio lanzada cuando no se encuentra un/a Cultivo en el repositorio.
 *
 * <p>RuntimeException no verificada. Capturada por GlobalExceptionHandler
 * y convertida en respuesta HTTP 404 Not Found.</p>
 */
public class CultivoNoEncontradoException extends RuntimeException {

    public CultivoNoEncontradoException(Long id) {
        super("Cultivo no encontrado con id: " + id);
    }

    public CultivoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
