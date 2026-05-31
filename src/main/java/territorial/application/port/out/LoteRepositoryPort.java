package territorial.application.port.out;

import territorial.domain.enums.EstadoLote;
import territorial.domain.model.Lote;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (repositorio) para la persistencia de lotes.
 *
 * <p>Implementado por
 * {@link territorial.infrastructure.adapter.out.persistence.repository.LoteRepositoryAdapter}.</p>
 */
public interface LoteRepositoryPort {

    Lote guardar(Lote lote);

    Optional<Lote> buscarPorId(Long id);

    List<Lote> buscarTodos();

    List<Lote> buscarPorCultivoId(Long cultivoId);

    List<Lote> buscarPorEstado(EstadoLote estado);

    List<Lote> buscarConAltaPlaga();

    void eliminar(Long id);

    boolean existePorId(Long id);

    void asociarPlaga(Long loteId, Long plagaId);

    void desasociarPlaga(Long loteId, Long plagaId);
}
