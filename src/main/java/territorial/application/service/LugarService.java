package territorial.application.service;

import territorial.application.port.in.GestionarLugarUseCase;
import territorial.application.port.out.LugarRepositoryPort;
import territorial.application.port.out.MunicipioRepositoryPort;
import territorial.domain.exception.LugarNoEncontradoException;
import territorial.domain.model.LugarProduccion;
import territorial.domain.model.Municipio;
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
public class LugarService implements GestionarLugarUseCase {

    private final LugarRepositoryPort lugarRepository;
    private final MunicipioRepositoryPort municipioRepository;

    @Override
    public LugarProduccion crear(LugarProduccion lugar) {
        log.info("Creando lugar de producción: {}", lugar.getNombre());
        validarMunicipio(lugar.getMunicipioId());
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
        if (lugar.getMunicipioId() != null) {
            validarMunicipio(lugar.getMunicipioId());
            existente.setMunicipio(lugar.getMunicipio());
        }
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
        validarMunicipio(municipioId);
        return lugarRepository.buscarPorMunicipioId(municipioId);
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

    private void validarMunicipio(Long municipioId) {
        if (municipioId != null && !municipioRepository.existePorId(municipioId)) {
            throw new IllegalArgumentException("Municipio no encontrado con id: " + municipioId);
        }
    }
}
