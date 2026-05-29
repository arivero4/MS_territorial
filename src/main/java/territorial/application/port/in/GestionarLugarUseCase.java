package territorial.application.port.in;

import territorial.domain.model.LugarProduccion;

import java.util.List;

public interface GestionarLugarUseCase {

    LugarProduccion crear(LugarProduccion lugar);

    LugarProduccion actualizar(Long id, LugarProduccion lugar);

    void eliminar(Long id);

    LugarProduccion obtenerPorId(Long id);

    List<LugarProduccion> listarTodos();

    List<LugarProduccion> listarPorMunicipio(Long municipioId);

    LugarProduccion activar(Long id);

    LugarProduccion desactivar(Long id);
}
