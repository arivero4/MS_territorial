package territorial.application.port.out;

import territorial.domain.model.Municipio;
import territorial.domain.valueobject.CodigoDane;

import java.util.List;
import java.util.Optional;

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
