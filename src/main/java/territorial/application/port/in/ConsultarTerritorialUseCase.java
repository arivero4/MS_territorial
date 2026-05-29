package territorial.application.port.in;

import territorial.domain.model.*;

import java.util.List;

public interface ConsultarTerritorialUseCase {

    List<Departamento> listarDepartamentos();

    Departamento obtenerDepartamento(Long id);

    List<Municipio> listarMunicipiosPorDepartamento(Long departamentoId);

    Municipio obtenerMunicipio(Long id);

    List<LugarProduccion> listarLugaresPorMunicipio(Long municipioId);

    List<Predio> listarPrediosPorLugar(Long lugarId);

    List<Cultivo> listarCultivosPorPredio(Long predioId);

    List<Lote> listarLotesPorCultivo(Long cultivoId);

    List<Plaga> listarPlagasPorLote(Long loteId);

    List<Plaga> listarPlagasPorCultivo(Long cultivoId);

    List<Lote> listarLotesConAltaPlaga();
}
