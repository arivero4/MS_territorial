package territorial.application.service;

import territorial.application.port.in.GestionarCultivoUseCase;
import territorial.application.port.out.CultivoRepositoryPort;
import territorial.application.port.out.PlagaRepositoryPort;
import territorial.domain.exception.CultivoNoEncontradoException;
import territorial.domain.model.Cultivo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de aplicación que implementa {@link territorial.application.port.in.GestionarCultivoUseCase}.
 *
 * <p>Gestiona el catálogo de cultivos hortifrutícolas: creación, actualización, activación
 * y asociación de plagas fitosanitarias a cada cultivo.</p>
 *
 * <p>{@code @Transactional} aplica a todos los métodos de escritura. Las consultas
 * usan {@code readOnly = true} donde corresponde.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CultivoService implements GestionarCultivoUseCase {

    private final CultivoRepositoryPort cultivoRepository;
    // PredioRepositoryPort eliminado: Cultivo NO tiene relación directa con Predio
    private final PlagaRepositoryPort plagaRepository;

    @Override
    public Cultivo crear(Cultivo cultivo) {
        log.info("Creando cultivo: {}", cultivo.getNombreComun());
        // Cultivo no valida predio — su jerarquía es: LugarProduccion → Lote → Cultivo
        if (cultivo.getFechaInicio() != null && cultivo.getFechaEstimadaCosecha() != null
                && cultivo.getFechaEstimadaCosecha().isBefore(cultivo.getFechaInicio())) {
            throw new IllegalArgumentException(
                    "La fecha estimada de cosecha no puede ser anterior a la fecha de inicio");
        }
        cultivo.setActivo(true);
        cultivo.setFechaCreacion(LocalDateTime.now());
        cultivo.setFechaActualizacion(LocalDateTime.now());
        Cultivo creado = cultivoRepository.guardar(cultivo);
        log.info("Cultivo creado con id: {}", creado.getId());
        return creado;
    }

    @Override
    public Cultivo actualizar(Long id, Cultivo cultivo) {
        log.info("Actualizando cultivo id: {}", id);
        Cultivo existente = cultivoRepository.buscarPorId(id)
                .orElseThrow(() -> new CultivoNoEncontradoException(id));
        existente.setNombreVariedad(cultivo.getNombreVariedad());
        existente.setNombreCientifico(cultivo.getNombreCientifico());
        existente.setNombreComun(cultivo.getNombreComun());
        existente.setDescripcion(cultivo.getDescripcion());
        existente.setFechaInicio(cultivo.getFechaInicio());
        existente.setFechaEstimadaCosecha(cultivo.getFechaEstimadaCosecha());
        existente.setFechaActualizacion(LocalDateTime.now());
        return cultivoRepository.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando cultivo id: {}", id);
        if (!cultivoRepository.existePorId(id)) {
            throw new CultivoNoEncontradoException(id);
        }
        cultivoRepository.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Cultivo obtenerPorId(Long id) {
        return cultivoRepository.buscarPorId(id)
                .orElseThrow(() -> new CultivoNoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> listarTodos() {
        return cultivoRepository.buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> listarPorPredio(Long predioId) {
        // Cultivo no tiene FK a Predio — retorna todos como fallback
        return cultivoRepository.buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> listarEnTemporada() {
        return cultivoRepository.buscarEnTemporada();
    }

    @Override
    public Cultivo activar(Long id) {
        Cultivo cultivo = cultivoRepository.buscarPorId(id)
                .orElseThrow(() -> new CultivoNoEncontradoException(id));
        cultivo.activar();
        return cultivoRepository.guardar(cultivo);
    }

    @Override
    public Cultivo desactivar(Long id) {
        Cultivo cultivo = cultivoRepository.buscarPorId(id)
                .orElseThrow(() -> new CultivoNoEncontradoException(id));
        cultivo.desactivar();
        return cultivoRepository.guardar(cultivo);
    }

    @Override
    public Cultivo asociarPlaga(Long cultivoId, Long plagaId) {
        if (!cultivoRepository.existePorId(cultivoId)) {
            throw new CultivoNoEncontradoException(cultivoId);
        }
        if (!plagaRepository.existePorId(plagaId)) {
            throw new IllegalArgumentException("Plaga no encontrada con id: " + plagaId);
        }
        cultivoRepository.asociarPlaga(cultivoId, plagaId);
        return cultivoRepository.buscarPorId(cultivoId).orElseThrow();
    }

    @Override
    public Cultivo desasociarPlaga(Long cultivoId, Long plagaId) {
        if (!cultivoRepository.existePorId(cultivoId)) {
            throw new CultivoNoEncontradoException(cultivoId);
        }
        cultivoRepository.desasociarPlaga(cultivoId, plagaId);
        return cultivoRepository.buscarPorId(cultivoId).orElseThrow();
    }
}
