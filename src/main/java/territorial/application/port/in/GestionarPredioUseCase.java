package territorial.application.port.in;

import territorial.domain.model.Predio;

import java.util.List;

public interface GestionarPredioUseCase {

    Predio crear(Predio predio);

    Predio actualizar(Long id, Predio predio);

    void eliminar(Long id);

    Predio obtenerPorId(Long id);

    List<Predio> listarTodos();

    List<Predio> listarPorLugarProduccion(Long lugarProduccionId);

    List<Predio> buscarPorNumeroPredial(String numeroPredial);

    Predio activar(Long id);

    Predio desactivar(Long id);
}
