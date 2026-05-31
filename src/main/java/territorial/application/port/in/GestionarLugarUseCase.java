package territorial.application.port.in;

import territorial.domain.model.LugarProduccion;

import java.util.List;

/**
 * Puerto de entrada (caso de uso) para la gestión de Lugares de Producción.
 *
 * <p>Un lugar de producción (finca) contiene los lotes donde se realizan
 * las inspecciones fitosanitarias. Implementado por {@link territorial.application.service.LugarService}.</p>
 */
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
