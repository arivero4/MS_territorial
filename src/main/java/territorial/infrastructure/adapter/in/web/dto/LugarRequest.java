package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Diccionario: lugar_produccion (id_lugar, nombre, area, estado, id_privilegio_grupo)
 */
@Data
public class LugarRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede superar 200 caracteres")
    private String nombre;

    @NotNull(message = "El área es obligatoria")
    @Positive(message = "El área debe ser un valor positivo")
    private Double area;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    // Referencia externa al privilegio de grupo (microservicio usuarios)
    private Long idPrivilegioGrupo;
}
