package territorial.application.port.in;

import territorial.domain.model.Predio;

import java.util.List;

/**
 * Puerto de entrada (caso de uso) para la gestión de predios rurales.
 *
 * <p>Un predio conecta la ubicación geográfica (Municipio) con la unidad productiva
 * (LugarProduccion). Implementado por {@link territorial.application.service.PredioService}.</p>
 */
public interface GestionarPredioUseCase {

    Predio crear(Predio predio);

    Predio actualizar(Long id, Predio predio);

    void eliminar(Long id);

    Predio obtenerPorId(Long id);

    List<Predio> listarTodos();

    List<Predio> listarPorLugarProduccion(Long lugarProduccionId);

    List<Predio> buscarPorNumeroPredial(String numeroPredial);

    Predio activar(Long id);

    Predio desactivar(Long id);
}
