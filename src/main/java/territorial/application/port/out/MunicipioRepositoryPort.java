package territorial.application.port.out;

import territorial.domain.model.Municipio;
import territorial.domain.valueobject.CodigoDane;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (repositorio) para la persistencia de municipios.
 *
 * <p>Implementado por
 * {@link territorial.infrastructure.adapter.out.persistence.repository.MunicipioRepositoryAdapter}.</p>
 */
public interface MunicipioRepositoryPort {

    Municipio guardar(Municipio municipio);

    Optional<Municipio> buscarPorId(Long id);

    Optional<Municipio> buscarPorCodigoDane(CodigoDane codigoDane);

    List<Municipio> buscarTodos();

    List<Municipio> buscarPorDepartamentoId(Long departamentoId);

    List<Municipio> buscarActivos();

    void eliminar(Long id);

    boolean existePorId(Long id);

    boolean existePorCodigoDane(CodigoDane codigoDane);
}
