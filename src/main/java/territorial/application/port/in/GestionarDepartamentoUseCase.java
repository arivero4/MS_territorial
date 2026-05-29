package territorial.application.port.in;

import territorial.domain.model.Departamento;

import java.util.List;

/**
 * Puerto de entrada para la gestión de departamentos.
 * Define el contrato que el dominio expone hacia el exterior.
 * Los adaptadores de entrada (controllers) dependen de esta interfaz, nunca de la implementación.
 */
public interface GestionarDepartamentoUseCase {

    Departamento crear(Departamento departamento);

    Departamento actualizar(Long id, Departamento departamento);

    void eliminar(Long id);

    Departamento obtenerPorId(Long id);

    List<Departamento> listarTodos();

    List<Departamento> listarActivos();

    Departamento activar(Long id);

    Departamento desactivar(Long id);
}
