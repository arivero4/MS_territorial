package territorial.application.port.in;

import territorial.domain.model.Cultivo;

import java.util.List;

/**
 * Puerto de entrada (caso de uso) para la gestión del catálogo de cultivos.
 *
 * <p>Define las operaciones CRUD y de ciclo de vida de los cultivos hortifrutícolas.
 * Implementado por {@link territorial.application.service.CultivoService}.</p>
 *
 * <p>Los cultivos se asignan a los lotes y tienen plagas fitosanitarias asociadas.</p>
 */
public interface GestionarCultivoUseCase {

    Cultivo crear(Cultivo cultivo);

    Cultivo actualizar(Long id, Cultivo cultivo);

    void eliminar(Long id);

    Cultivo obtenerPorId(Long id);

    List<Cultivo> listarTodos();

    List<Cultivo> listarPorPredio(Long predioId);

    List<Cultivo> listarEnTemporada();

    Cultivo activar(Long id);

    Cultivo desactivar(Long id);

    Cultivo asociarPlaga(Long cultivoId, Long plagaId);

    Cultivo desasociarPlaga(Long cultivoId, Long plagaId);
}
