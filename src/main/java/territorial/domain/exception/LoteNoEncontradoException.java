package territorial.domain.exception;

public class LoteNoEncontradoException extends RuntimeException {

    public LoteNoEncontradoException(Long id) {
        super("Lote no encontrado con id: " + id);
    }

    public LoteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
