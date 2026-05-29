package territorial.application.port.out;

import territorial.domain.model.Cultivo;

import java.util.List;
import java.util.Optional;

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
