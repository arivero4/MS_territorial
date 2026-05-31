package territorial.application.service;

import territorial.application.port.in.GestionarLugarUseCase;
import territorial.application.port.out.LugarRepositoryPort;
import territorial.domain.exception.LugarNoEncontradoException;
import territorial.domain.model.LugarProduccion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de aplicación que implementa {@link territorial.application.port.in.GestionarLugarUseCase}.
 *
 * <p>Gestiona los lugares de producción (fincas). Un lugar de producción
 * no tiene municipio directo; se ubica geográficamente navegando: Predio → Municipio.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LugarService implements GestionarLugarUseCase {

    private final LugarRepositoryPort lugarRepository;

    @Override
    public LugarProduccion crear(LugarProduccion lugar) {
        log.info("Creando lugar de producción: {}", lugar.getNombre());
        // LugarProduccion NO tiene municipio directo — se vincula a través de Predio
        lugar.setActivo(true);
        lugar.setFechaCreacion(LocalDateTime.now());
        lugar.setFechaActualizacion(LocalDateTime.now());
        LugarProduccion creado = lugarRepository.guardar(lugar);
        log.info("Lugar de producción creado con id: {}", creado.getId());
        return creado;
    }

    @Override
    public LugarProduccion actualizar(Long id, LugarProduccion lugar) {
        log.info("Actualizando lugar de producción id: {}", id);
        LugarProduccion existente = lugarRepository.buscarPorId(id)
                .orElseThrow(() -> new LugarNoEncontradoException(id));
        existente.setNombre(lugar.getNombre());
        existente.setDescripcion(lugar.getDescripcion());
        existente.setArea(lugar.getArea());
        existente.setVereda(lugar.getVereda());
        existente.setCoordenadas(lugar.getCoordenadas());
        existente.setFechaActualizacion(LocalDateTime.now());
        return lugarRepository.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando lugar de producción id: {}", id);
        if (!lugarRepository.existePorId(id)) {
            throw new LugarNoEncontradoException(id);
        }
        lugarRepository.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public LugarProduccion obtenerPorId(Long id) {
        return lugarRepository.buscarPorId(id)
                .orElseThrow(() -> new LugarNoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarProduccion> listarTodos() {
        return lugarRepository.buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarProduccion> listarPorMunicipio(Long municipioId) {
        // LugarProduccion ya no tiene FK a municipio. Retorna todos como fallback.
        return lugarRepository.buscarTodos();
    }

    @Override
    public LugarProduccion activar(Long id) {
        LugarProduccion lugar = lugarRepository.buscarPorId(id)
                .orElseThrow(() -> new LugarNoEncontradoException(id));
        lugar.activar();
        return lugarRepository.guardar(lugar);
    }

    @Override
    public LugarProduccion desactivar(Long id) {
        LugarProduccion lugar = lugarRepository.buscarPorId(id)
                .orElseThrow(() -> new LugarNoEncontradoException(id));
        lugar.desactivar();
        return lugarRepository.guardar(lugar);
    }

}
