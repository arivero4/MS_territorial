package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Diccionario: plaga (id_plaga, nombre_cientifico, nombre_comun, id_cultivo)
 */
@Data
public class PlagaRequest {

    @NotBlank(message = "El nombre común es obligatorio")
    @Size(max = 200, message = "El nombre común no puede superar 200 caracteres")
    private String nombreComun;

    @NotBlank(message = "El nombre científico es obligatorio")
    @Size(max = 200, message = "El nombre científico no puede superar 200 caracteres")
    private String nombreCientifico;

    @NotNull(message = "El cultivo es obligatorio")
    private Long idCultivo;
}
