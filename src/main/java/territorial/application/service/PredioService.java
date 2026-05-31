package territorial.application.service;

import territorial.application.port.in.GestionarPredioUseCase;
import territorial.application.port.out.LugarRepositoryPort;
import territorial.application.port.out.PredioRepositoryPort;
import territorial.domain.exception.PredioNoEncontradoException;
import territorial.domain.model.Predio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de aplicación que implementa {@link territorial.application.port.in.GestionarPredioUseCase}.
 *
 * <p>Gestiona los predios rurales. Valida la existencia del lugar de producción
 * y la unicidad del número predial antes de persistir.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PredioService implements GestionarPredioUseCase {

    private final PredioRepositoryPort predioRepository;
    private final LugarRepositoryPort lugarRepository;

    @Override
    public Predio crear(Predio predio) {
        log.info("Creando predio: {}", predio.getNombre());
        validarLugar(predio.getLugarProduccionId());
        if (predio.getNumeroPredial() != null
                && predioRepository.existePorNumeroPredial(predio.getNumeroPredial())) {
            throw new IllegalArgumentException(
                    "Ya existe un predio con número predial: " + predio.getNumeroPredial());
        }
        predio.setActivo(true);
        predio.setFechaCreacion(LocalDateTime.now());
        predio.setFechaActualizacion(LocalDateTime.now());
        Predio creado = predioRepository.guardar(predio);
        log.info("Predio creado con id: {}", creado.getId());
        return creado;
    }

    @Override
    public Predio actualizar(Long id, Predio predio) {
        log.info("Actualizando predio id: {}", id);
        Predio existente = predioRepository.buscarPorId(id)
                .orElseThrow(() -> new PredioNoEncontradoException(id));
        if (predio.getLugarProduccionId() != null) {
            validarLugar(predio.getLugarProduccionId());
            existente.setLugarProduccion(predio.getLugarProduccion());
        }
        existente.setNombre(predio.getNombre());
        existente.setNumeroPredial(predio.getNumeroPredial());
        existente.setMatriculaInmobiliaria(predio.getMatriculaInmobiliaria());
        existente.setArea(predio.getArea());
        existente.setVereda(predio.getVereda());
        existente.setDescripcion(predio.getDescripcion());
        existente.setCoordenadas(predio.getCoordenadas());
        existente.setFechaActualizacion(LocalDateTime.now());
        return predioRepository.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando predio id: {}", id);
        if (!predioRepository.existePorId(id)) {
            throw new PredioNoEncontradoException(id);
        }
        predioRepository.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Predio obtenerPorId(Long id) {
        return predioRepository.buscarPorId(id)
                .orElseThrow(() -> new PredioNoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Predio> listarTodos() {
        return predioRepository.buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Predio> listarPorLugarProduccion(Long lugarProduccionId) {
        validarLugar(lugarProduccionId);
        return predioRepository.buscarPorLugarProduccionId(lugarProduccionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Predio> buscarPorNumeroPredial(String numeroPredial) {
        return predioRepository.buscarPorNumeroPredial(numeroPredial)
                .map(List::of)
                .orElse(List.of());
    }

    @Override
    public Predio activar(Long id) {
        Predio predio = predioRepository.buscarPorId(id)
                .orElseThrow(() -> new PredioNoEncontradoException(id));
        predio.activar();
        return predioRepository.guardar(predio);
    }

    @Override
    public Predio desactivar(Long id) {
        Predio predio = predioRepository.buscarPorId(id)
                .orElseThrow(() -> new PredioNoEncontradoException(id));
        predio.desactivar();
        return predioRepository.guardar(predio);
    }

    private void validarLugar(Long lugarId) {
        if (lugarId != null && !lugarRepository.existePorId(lugarId)) {
            throw new IllegalArgumentException("Lugar de producción no encontrado con id: " + lugarId);
        }
    }
}
