package territorial.application.service;

import territorial.application.port.in.GestionarMunicipioUseCase;
import territorial.application.port.out.DepartamentoRepositoryPort;
import territorial.application.port.out.MunicipioRepositoryPort;
import territorial.domain.exception.DepartamentoNoEncontradoException;
import territorial.domain.exception.MunicipioNoEncontradoException;
import territorial.domain.model.Municipio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de aplicación para Municipio.
 * Implementa el puerto de entrada GestionarMunicipioUseCase.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GestionarMunicipioService implements GestionarMunicipioUseCase {

    private final MunicipioRepositoryPort municipioRepository;
    private final DepartamentoRepositoryPort departamentoRepository;

    @Override
    public Municipio crear(Municipio municipio) {
        log.info("Creando municipio: {}", municipio.getNombre());
        if (municipio.getDepartamentoId() != null
                && !departamentoRepository.existePorId(municipio.getDepartamentoId())) {
            throw new IllegalArgumentException(
                    "Departamento no encontrado: " + municipio.getDepartamentoId());
        }
        if (municipio.getCodigoDane() != null
                && municipioRepository.existePorCodigoDane(municipio.getCodigoDane())) {
            throw new IllegalArgumentException(
                    "Ya existe un municipio con código DANE: " + municipio.getCodigoDane());
        }
        municipio.setActivo(true);
        municipio.setFechaCreacion(LocalDateTime.now());
        municipio.setFechaActualizacion(LocalDateTime.now());
        Municipio creado = municipioRepository.guardar(municipio);
        log.info("Municipio creado con id: {}", creado.getId());
        return creado;
    }

    @Override
    public Municipio actualizar(Long id, Municipio municipio) {
        log.info("Actualizando municipio id: {}", id);
        Municipio existente = municipioRepository.buscarPorId(id)
                .orElseThrow(() -> new MunicipioNoEncontradoException(id));
        if (municipio.getDepartamentoId() != null
                && !departamentoRepository.existePorId(municipio.getDepartamentoId())) {
            throw new IllegalArgumentException(
                    "Departamento no encontrado: " + municipio.getDepartamentoId());
        }
        existente.setNombre(municipio.getNombre());
        if (municipio.getCodigoDane() != null) existente.setCodigoDane(municipio.getCodigoDane());
        if (municipio.getDepartamento() != null) existente.setDepartamento(municipio.getDepartamento());
        existente.setFechaActualizacion(LocalDateTime.now());
        return municipioRepository.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando municipio id: {}", id);
        if (!municipioRepository.existePorId(id)) {
            throw new MunicipioNoEncontradoException(id);
        }
        municipioRepository.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Municipio obtenerPorId(Long id) {
        return municipioRepository.buscarPorId(id)
                .orElseThrow(() -> new MunicipioNoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Municipio> listarTodos() {
        return municipioRepository.buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Municipio> listarPorDepartamento(Long departamentoId) {
        if (!departamentoRepository.existePorId(departamentoId)) {
            throw new DepartamentoNoEncontradoException(departamentoId);
        }
        return municipioRepository.buscarPorDepartamentoId(departamentoId);
    }

    @Override
    public Municipio activar(Long id) {
        Municipio m = municipioRepository.buscarPorId(id)
                .orElseThrow(() -> new MunicipioNoEncontradoException(id));
        m.setActivo(true);
        m.setFechaActualizacion(LocalDateTime.now());
        return municipioRepository.guardar(m);
    }

    @Override
    public Municipio desactivar(Long id) {
        Municipio m = municipioRepository.buscarPorId(id)
                .orElseThrow(() -> new MunicipioNoEncontradoException(id));
        m.setActivo(false);
        m.setFechaActualizacion(LocalDateTime.now());
        return municipioRepository.guardar(m);
    }
}
