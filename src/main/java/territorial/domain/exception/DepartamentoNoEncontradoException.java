package territorial.domain.exception;

/**
 * Excepcion de dominio lanzada cuando no se encuentra un/a Departamento en el repositorio.
 *
 * <p>RuntimeException no verificada. Capturada por GlobalExceptionHandler
 * y convertida en respuesta HTTP 404 Not Found.</p>
 */
public class DepartamentoNoEncontradoException extends RuntimeException {

    public DepartamentoNoEncontradoException(Long id) {
        super("Departamento no encontrado con id: " + id);
    }

    public DepartamentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
