package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Diccionario: cultivo (id_cultivo, nombre_variedad, nombre_cientifico, nombre_comun, descripcion)
 */
@Data
public class CultivoRequest {

    @NotBlank(message = "El nombre de la variedad es obligatorio")
    @Size(max = 200, message = "El nombre de variedad no puede superar 200 caracteres")
    private String nombreVariedad;

    @Size(max = 200, message = "El nombre científico no puede superar 200 caracteres")
    private String nombreCientifico;

    @NotBlank(message = "El nombre común es obligatorio")
    @Size(max = 200, message = "El nombre común no puede superar 200 caracteres")
    private String nombreComun;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;
}
