package territorial.application.service;

import territorial.application.port.in.GestionarDepartamentoUseCase;
import territorial.application.port.out.DepartamentoRepositoryPort;
import territorial.domain.exception.DepartamentoNoEncontradoException;
import territorial.domain.model.Departamento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de aplicación para Departamento.
 * Implementa el puerto de entrada GestionarDepartamentoUseCase.
 * Coordina la lógica de negocio sin conocer detalles de infraestructura.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GestionarDepartamentoService implements GestionarDepartamentoUseCase {

    private final DepartamentoRepositoryPort departamentoRepository;

    @Override
    public Departamento crear(Departamento departamento) {
        log.info("Creando departamento: {}", departamento.getNombre());
        if (departamento.getCodigoDane() != null
                && departamentoRepository.existePorCodigoDane(departamento.getCodigoDane())) {
            throw new IllegalArgumentException(
                    "Ya existe un departamento con código DANE: " + departamento.getCodigoDane());
        }
        departamento.setActivo(true);
        departamento.setFechaCreacion(LocalDateTime.now());
        departamento.setFechaActualizacion(LocalDateTime.now());
        Departamento creado = departamentoRepository.guardar(departamento);
        log.info("Departamento creado con id: {}", creado.getId());
        return creado;
    }

    @Override
    public Departamento actualizar(Long id, Departamento departamento) {
        log.info("Actualizando departamento id: {}", id);
        Departamento existente = departamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new DepartamentoNoEncontradoException(id));
        if (departamento.getCodigoDane() != null
                && !departamento.getCodigoDane().equals(existente.getCodigoDane())
                && departamentoRepository.existePorCodigoDane(departamento.getCodigoDane())) {
            throw new IllegalArgumentException(
                    "Ya existe otro departamento con código DANE: " + departamento.getCodigoDane());
        }
        existente.setNombre(departamento.getNombre());
        if (departamento.getCodigoDane() != null) {
            existente.setCodigoDane(departamento.getCodigoDane());
        }
        existente.setFechaActualizacion(LocalDateTime.now());
        return departamentoRepository.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando departamento id: {}", id);
        if (!departamentoRepository.existePorId(id)) {
            throw new DepartamentoNoEncontradoException(id);
        }
        departamentoRepository.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Departamento obtenerPorId(Long id) {
        return departamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new DepartamentoNoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Departamento> listarTodos() {
        return departamentoRepository.buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Departamento> listarActivos() {
        return departamentoRepository.buscarActivos();
    }

    @Override
    public Departamento activar(Long id) {
        Departamento d = departamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new DepartamentoNoEncontradoException(id));
        d.activar();
        return departamentoRepository.guardar(d);
    }

    @Override
    public Departamento desactivar(Long id) {
        Departamento d = departamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new DepartamentoNoEncontradoException(id));
        d.desactivar();
        return departamentoRepository.guardar(d);
    }
}
