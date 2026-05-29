package territorial.application.service;

import territorial.application.port.in.GestionarPlagaUseCase;
import territorial.application.port.out.PlagaRepositoryPort;
import territorial.domain.model.Plaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlagaService implements GestionarPlagaUseCase {

    private final PlagaRepositoryPort plagaRepository;

    @Override
    public Plaga crear(Plaga plaga) {
        log.info("Creando plaga: {}", plaga.getNombreComun());
        plaga.setActivo(true);
        plaga.setFechaCreacion(LocalDateTime.now());
        plaga.setFechaActualizacion(LocalDateTime.now());
        Plaga creada = plagaRepository.guardar(plaga);
        log.info("Plaga creada con id: {}", creada.getId());
        return creada;
    }

    @Override
    public Plaga actualizar(Long id, Plaga plaga) {
        log.info("Actualizando plaga id: {}", id);
        Plaga existente = plagaRepository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Plaga no encontrada con id: " + id));
        existente.setNombreCientifico(plaga.getNombreCientifico());
        existente.setNombreComun(plaga.getNombreComun());
        existente.setDescripcion(plaga.getDescripcion());
        existente.setTipo(plaga.getTipo());
        existente.setNivelRiesgo(plaga.getNivelRiesgo());
        existente.setSintomas(plaga.getSintomas());
        existente.setTratamiento(plaga.getTratamiento());
        existente.setFechaActualizacion(LocalDateTime.now());
        return plagaRepository.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando plaga id: {}", id);
        if (!plagaRepository.existePorId(id)) {
            throw new NoSuchElementException("Plaga no encontrada con id: " + id);
        }
        plagaRepository.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Plaga obtenerPorId(Long id) {
        return plagaRepository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Plaga no encontrada con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> listarTodas() {
        return plagaRepository.buscarTodas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> listarPorTipo(String tipo) {
        return plagaRepository.buscarPorTipo(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> listarPorNivelRiesgo(String nivelRiesgo) {
        return plagaRepository.buscarPorNivelRiesgo(nivelRiesgo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> buscarPorNombre(String nombre) {
        return plagaRepository.buscarPorNombreContiene(nombre);
    }
}
