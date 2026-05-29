package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PlagaRequest {

    @NotBlank(message = "El nombre común es obligatorio")
    @Size(max = 200, message = "El nombre común no puede superar 200 caracteres")
    private String nombreComun;

    @Size(max = 200, message = "El nombre científico no puede superar 200 caracteres")
    private String nombreCientifico;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;

    @Size(max = 100, message = "El tipo no puede superar 100 caracteres")
    private String tipo;

    @Pattern(regexp = "BAJO|MEDIO|ALTO", message = "El nivel de riesgo debe ser BAJO, MEDIO o ALTO")
    private String nivelRiesgo;

    @Size(max = 1000, message = "Los síntomas no pueden superar 1000 caracteres")
    private String sintomas;

    @Size(max = 1000, message = "El tratamiento no puede superar 1000 caracteres")
    private String tratamiento;
}
