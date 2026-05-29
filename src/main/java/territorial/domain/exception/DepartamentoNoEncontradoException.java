package territorial.domain.exception;

public class DepartamentoNoEncontradoException extends RuntimeException {

    public DepartamentoNoEncontradoException(Long id) {
        super("Departamento no encontrado con id: " + id);
    }

    public DepartamentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
