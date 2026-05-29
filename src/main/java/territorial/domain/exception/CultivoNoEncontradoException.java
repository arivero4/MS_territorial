package territorial.domain.exception;

public class CultivoNoEncontradoException extends RuntimeException {

    public CultivoNoEncontradoException(Long id) {
        super("Cultivo no encontrado con id: " + id);
    }

    public CultivoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
