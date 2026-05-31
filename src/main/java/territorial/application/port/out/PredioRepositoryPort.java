package territorial.application.port.out;

import territorial.domain.model.Predio;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (repositorio) para la persistencia de predios.
 *
 * <p>Implementado por
 * {@link territorial.infrastructure.adapter.out.persistence.repository.PredioRepositoryAdapter}.</p>
 */
public interface PredioRepositoryPort {

    Predio guardar(Predio predio);

    Optional<Predio> buscarPorId(Long id);

    Optional<Predio> buscarPorNumeroPredial(String numeroPredial);

    List<Predio> buscarTodos();

    List<Predio> buscarPorLugarProduccionId(Long lugarProduccionId);

    List<Predio> buscarActivos();

    List<Predio> buscarPorNombreContiene(String nombre);

    void eliminar(Long id);

    boolean existePorId(Long id);

    boolean existePorNumeroPredial(String numeroPredial);
}
