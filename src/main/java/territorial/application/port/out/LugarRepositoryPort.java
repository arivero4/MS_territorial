package territorial.application.port.out;

import territorial.domain.model.LugarProduccion;

import java.util.List;
import java.util.Optional;

public interface LugarRepositoryPort {

    LugarProduccion guardar(LugarProduccion lugar);

    Optional<LugarProduccion> buscarPorId(Long id);

    List<LugarProduccion> buscarTodos();

    List<LugarProduccion> buscarPorMunicipioId(Long municipioId);

    List<LugarProduccion> buscarActivos();

    void eliminar(Long id);

    boolean existePorId(Long id);
}
