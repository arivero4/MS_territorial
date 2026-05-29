package territorial.domain.exception;

public class LugarNoEncontradoException extends RuntimeException {

    public LugarNoEncontradoException(Long id) {
        super("Lugar de producción no encontrado con id: " + id);
    }

    public LugarNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
