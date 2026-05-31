package territorial.application.port.out;

import territorial.domain.model.Cultivo;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (repositorio) para la persistencia de cultivos.
 *
 * <p>Abstrae el mecanismo de almacenamiento. Implementado por
 * {@link territorial.infrastructure.adapter.out.persistence.repository.CultivoRepositoryAdapter}
 * usando JPA/Hibernate sobre Oracle 10g.</p>
 */
public interface CultivoRepositoryPort {

    Cultivo guardar(Cultivo cultivo);

    Optional<Cultivo> buscarPorId(Long id);

    List<Cultivo> buscarTodos();

    List<Cultivo> buscarPorPredioId(Long predioId);

    List<Cultivo> buscarEnTemporada();

    List<Cultivo> buscarActivos();

    void eliminar(Long id);

    boolean existePorId(Long id);

    void asociarPlaga(Long cultivoId, Long plagaId);

    void desasociarPlaga(Long cultivoId, Long plagaId);
}
