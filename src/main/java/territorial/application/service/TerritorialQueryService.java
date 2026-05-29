package territorial.application.service;

import territorial.application.port.in.ConsultarTerritorialUseCase;
import territorial.application.port.out.*;
import territorial.domain.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TerritorialQueryService implements ConsultarTerritorialUseCase {

    private final DepartamentoRepositoryPort departamentoRepository;
    private final MunicipioRepositoryPort municipioRepository;
    private final LugarRepositoryPort lugarRepository;
    private final PredioRepositoryPort predioRepository;
    private final CultivoRepositoryPort cultivoRepository;
    private final LoteRepositoryPort loteRepository;
    private final PlagaRepositoryPort plagaRepository;

    @Override
    public List<Departamento> listarDepartamentos() {
        return departamentoRepository.buscarActivos();
    }

    @Override
    public Departamento obtenerDepartamento(Long id) {
        return departamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Departamento no encontrado con id: " + id));
    }

    @Override
    public List<Municipio> listarMunicipiosPorDepartamento(Long departamentoId) {
        if (!departamentoRepository.existePorId(departamentoId)) {
            throw new NoSuchElementException("Departamento no encontrado con id: " + departamentoId);
        }
        return municipioRepository.buscarPorDepartamentoId(departamentoId);
    }

    @Override
    public Municipio obtenerMunicipio(Long id) {
        return municipioRepository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Municipio no encontrado con id: " + id));
    }

    @Override
    public List<LugarProduccion> listarLugaresPorMunicipio(Long municipioId) {
        if (!municipioRepository.existePorId(municipioId)) {
            throw new NoSuchElementException("Municipio no encontrado con id: " + municipioId);
        }
        return lugarRepository.buscarPorMunicipioId(municipioId);
    }

    @Override
    public List<Predio> listarPrediosPorLugar(Long lugarId) {
        if (!lugarRepository.existePorId(lugarId)) {
            throw new NoSuchElementException("Lugar de producción no encontrado con id: " + lugarId);
        }
        return predioRepository.buscarPorLugarProduccionId(lugarId);
    }

    @Override
    public List<Cultivo> listarCultivosPorPredio(Long predioId) {
        if (!predioRepository.existePorId(predioId)) {
            throw new NoSuchElementException("Predio no encontrado con id: " + predioId);
        }
        return cultivoRepository.buscarPorPredioId(predioId);
    }

    @Override
    public List<Lote> listarLotesPorCultivo(Long cultivoId) {
        if (!cultivoRepository.existePorId(cultivoId)) {
            throw new NoSuchElementException("Cultivo no encontrado con id: " + cultivoId);
        }
        return loteRepository.buscarPorCultivoId(cultivoId);
    }

    @Override
    public List<Plaga> listarPlagasPorLote(Long loteId) {
        if (!loteRepository.existePorId(loteId)) {
            throw new NoSuchElementException("Lote no encontrado con id: " + loteId);
        }
        return plagaRepository.buscarPorLoteId(loteId);
    }

    @Override
    public List<Plaga> listarPlagasPorCultivo(Long cultivoId) {
        if (!cultivoRepository.existePorId(cultivoId)) {
            throw new NoSuchElementException("Cultivo no encontrado con id: " + cultivoId);
        }
        return plagaRepository.buscarPorCultivoId(cultivoId);
    }

    @Override
    public List<Lote> listarLotesConAltaPlaga() {
        return loteRepository.buscarConAltaPlaga();
    }
}
