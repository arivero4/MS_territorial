package territorial.application.port.in;

import territorial.domain.model.Plaga;

import java.util.List;

public interface GestionarPlagaUseCase {

    Plaga crear(Plaga plaga);

    Plaga actualizar(Long id, Plaga plaga);

    void eliminar(Long id);

    Plaga obtenerPorId(Long id);

    List<Plaga> listarTodas();

    List<Plaga> listarPorTipo(String tipo);

    List<Plaga> listarPorNivelRiesgo(String nivelRiesgo);

    List<Plaga> buscarPorNombre(String nombre);
}
