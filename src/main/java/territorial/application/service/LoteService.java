package territorial.application.service;

import territorial.application.port.in.GestionarLoteUseCase;
import territorial.application.port.out.CultivoRepositoryPort;
import territorial.application.port.out.LoteRepositoryPort;
import territorial.application.port.out.PlagaRepositoryPort;
import territorial.domain.enums.EstadoLote;
import territorial.domain.exception.CultivoNoEncontradoException;
import territorial.domain.exception.LoteNoEncontradoException;
import territorial.domain.model.Lote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoteService implements GestionarLoteUseCase {

    private final LoteRepositoryPort loteRepository;
    private final CultivoRepositoryPort cultivoRepository;
    private final PlagaRepositoryPort plagaRepository;

    @Override
    public Lote crear(Lote lote) {
        log.info("Creando lote: {} para cultivo id: {}", lote.getNumero(), lote.getCultivoId());
        validarCultivo(lote.getCultivoId());
        if (lote.getEstado() == null) {
            lote.setEstado(EstadoLote.ACTIVO);
        }
        lote.setFechaCreacion(LocalDateTime.now());
        lote.setFechaActualizacion(LocalDateTime.now());
        Lote creado = loteRepository.guardar(lote);
        log.info("Lote creado con id: {}", creado.getId());
        return creado;
    }

    @Override
    public Lote actualizar(Long id, Lote lote) {
        log.info("Actualizando lote id: {}", id);
        Lote existente = loteRepository.buscarPorId(id)
                .orElseThrow(() -> new LoteNoEncontradoException(id));
        if (lote.getCultivoId() != null) {
            validarCultivo(lote.getCultivoId());
            existente.setCultivo(lote.getCultivo());
        }
        existente.setNumero(lote.getNumero());
        existente.setNombre(lote.getNombre());
        existente.setArea(lote.getArea());
        existente.setFechaSiembra(lote.getFechaSiembra());
        existente.setFechaCosechaEstimada(lote.getFechaCosechaEstimada());
        existente.setCoordenadas(lote.getCoordenadas());
        existente.setFechaActualizacion(LocalDateTime.now());
        return loteRepository.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando lote id: {}", id);
        if (!loteRepository.existePorId(id)) {
            throw new LoteNoEncontradoException(id);
        }
        loteRepository.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Lote obtenerPorId(Long id) {
        return loteRepository.buscarPorId(id)
                .orElseThrow(() -> new LoteNoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lote> listarTodos() {
        return loteRepository.buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lote> listarPorCultivo(Long cultivoId) {
        validarCultivo(cultivoId);
        return loteRepository.buscarPorCultivoId(cultivoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lote> listarPorEstado(EstadoLote estado) {
        return loteRepository.buscarPorEstado(estado);
    }

    @Override
    public Lote cambiarEstado(Long id, EstadoLote nuevoEstado) {
        log.info("Cambiando estado de lote id: {} a {}", id, nuevoEstado);
        Lote lote = loteRepository.buscarPorId(id)
                .orElseThrow(() -> new LoteNoEncontradoException(id));
        lote.setEstado(nuevoEstado);
        lote.setFechaActualizacion(LocalDateTime.now());
        return loteRepository.guardar(lote);
    }

    @Override
    public Lote iniciarProduccion(Long id) {
        Lote lote = loteRepository.buscarPorId(id)
                .orElseThrow(() -> new LoteNoEncontradoException(id));
        if (!lote.estaActivo()) {
            throw new IllegalStateException("El lote id: " + id + " no está en estado activo para iniciar producción");
        }
        lote.iniciarProduccion();
        return loteRepository.guardar(lote);
    }

    @Override
    public Lote cosechar(Long id) {
        Lote lote = loteRepository.buscarPorId(id)
                .orElseThrow(() -> new LoteNoEncontradoException(id));
        if (!lote.estaEnProduccion()) {
            throw new IllegalStateException("El lote id: " + id + " debe estar EN_PRODUCCION para cosechar");
        }
        lote.cosechar();
        return loteRepository.guardar(lote);
    }

    @Override
    public Lote asociarPlaga(Long loteId, Long plagaId) {
        if (!loteRepository.existePorId(loteId)) {
            throw new LoteNoEncontradoException(loteId);
        }
        if (!plagaRepository.existePorId(plagaId)) {
            throw new IllegalArgumentException("Plaga no encontrada con id: " + plagaId);
        }
        loteRepository.asociarPlaga(loteId, plagaId);
        return loteRepository.buscarPorId(loteId).orElseThrow();
    }

    @Override
    public Lote desasociarPlaga(Long loteId, Long plagaId) {
        if (!loteRepository.existePorId(loteId)) {
            throw new LoteNoEncontradoException(loteId);
        }
        loteRepository.desasociarPlaga(loteId, plagaId);
        return loteRepository.buscarPorId(loteId).orElseThrow();
    }

    private void validarCultivo(Long cultivoId) {
        if (cultivoId != null && !cultivoRepository.existePorId(cultivoId)) {
            throw new CultivoNoEncontradoException(cultivoId);
        }
    }
}
