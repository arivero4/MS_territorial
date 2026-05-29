package territorial.domain.exception;

public class PredioNoEncontradoException extends RuntimeException {

    public PredioNoEncontradoException(Long id) {
        super("Predio no encontrado con id: " + id);
    }

    public PredioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
