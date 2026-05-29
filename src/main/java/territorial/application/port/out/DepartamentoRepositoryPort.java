package territorial.application.port.out;

import territorial.domain.model.Departamento;
import territorial.domain.valueobject.CodigoDane;

import java.util.List;
import java.util.Optional;

public interface DepartamentoRepositoryPort {

    Departamento guardar(Departamento departamento);

    Optional<Departamento> buscarPorId(Long id);

    Optional<Departamento> buscarPorCodigoDane(CodigoDane codigoDane);

    List<Departamento> buscarTodos();

    List<Departamento> buscarActivos();

    void eliminar(Long id);

    boolean existePorId(Long id);

    boolean existePorCodigoDane(CodigoDane codigoDane);
}
