package territorial.application.port.in;

import territorial.domain.model.Plaga;

import java.util.List;

/**
 * Puerto de entrada (caso de uso) para la gestión del catálogo de plagas fitosanitarias.
 *
 * <p>Las plagas se asocian a cultivos específicos y son seleccionables por el
 * Asistente Técnico durante el conteo en vivo de una inspección.
 * Implementado por {@link territorial.application.service.PlagaService}.</p>
 */
public interface GestionarPlagaUseCase {

    Plaga crear(Plaga plaga);

    Plaga actualizar(Long id, Plaga plaga);

    void eliminar(Long id);

    Plaga obtenerPorId(Long id);

    List<Plaga> listarTodas();

    List<Plaga> listarPorTipo(String tipo);

    List<Plaga> listarPorNivelRiesgo(String nivelRiesgo);

    List<Plaga> buscarPorNombre(String nombre);
}
