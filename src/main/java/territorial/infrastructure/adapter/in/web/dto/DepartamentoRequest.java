package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/** DTO de entrada para crear o actualizar un departamento. */

@Data
public class DepartamentoRequest {

    @NotBlank(message = "El nombre del departamento es obligatorio")
    @Size(max = 200, message = "El nombre no puede superar 200 caracteres")
    private String nombre;

    @NotBlank(message = "El código DANE es obligatorio")
    @Pattern(regexp = "\\d{2}", message = "El código DANE del departamento debe tener exactamente 2 dígitos")
    private String codigoDane;
}
