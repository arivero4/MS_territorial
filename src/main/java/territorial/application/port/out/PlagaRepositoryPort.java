package territorial.application.port.out;

import territorial.domain.model.Plaga;

import java.util.List;
import java.util.Optional;

public interface PlagaRepositoryPort {

    Plaga guardar(Plaga plaga);

    Optional<Plaga> buscarPorId(Long id);

    List<Plaga> buscarTodas();

    List<Plaga> buscarPorTipo(String tipo);

    List<Plaga> buscarPorNivelRiesgo(String nivelRiesgo);

    List<Plaga> buscarPorNombreContiene(String nombre);

    List<Plaga> buscarPorLoteId(Long loteId);

    List<Plaga> buscarPorCultivoId(Long cultivoId);

    void eliminar(Long id);

    boolean existePorId(Long id);
}
