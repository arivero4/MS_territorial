package territorial.domain.exception;

public class MunicipioNoEncontradoException extends RuntimeException {

    public MunicipioNoEncontradoException(Long id) {
        super("Municipio no encontrado con id: " + id);
    }

    public MunicipioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
