package territorial.application.port.in;

import territorial.domain.enums.EstadoLote;
import territorial.domain.model.Lote;

import java.util.List;

public interface GestionarLoteUseCase {

    Lote crear(Lote lote);

    Lote actualizar(Long id, Lote lote);

    void eliminar(Long id);

    Lote obtenerPorId(Long id);

    List<Lote> listarTodos();

    List<Lote> listarPorCultivo(Long cultivoId);

    List<Lote> listarPorEstado(EstadoLote estado);

    Lote cambiarEstado(Long id, EstadoLote nuevoEstado);

    Lote iniciarProduccion(Long id);

    Lote cosechar(Long id);

    Lote asociarPlaga(Long loteId, Long plagaId);

    Lote desasociarPlaga(Long loteId, Long plagaId);
}
