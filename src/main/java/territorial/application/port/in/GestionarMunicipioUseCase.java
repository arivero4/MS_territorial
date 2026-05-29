package territorial.application.port.in;

import territorial.domain.model.Municipio;

import java.util.List;

/**
 * Puerto de entrada para la gestión de municipios.
 * Define el contrato que el dominio expone hacia el exterior.
 */
public interface GestionarMunicipioUseCase {

    Municipio crear(Municipio municipio);

    Municipio actualizar(Long id, Municipio municipio);

    void eliminar(Long id);

    Municipio obtenerPorId(Long id);

    List<Municipio> listarTodos();

    List<Municipio> listarPorDepartamento(Long departamentoId);

    Municipio activar(Long id);

    Municipio desactivar(Long id);
}
