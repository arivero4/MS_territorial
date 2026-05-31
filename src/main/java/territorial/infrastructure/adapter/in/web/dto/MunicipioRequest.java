package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/** DTO de entrada para municipio. Requiere departamentoId y codigo DANE. */

@Data
public class MunicipioRequest {

    @NotBlank(message = "El nombre del municipio es obligatorio")
    @Size(max = 200, message = "El nombre no puede superar 200 caracteres")
    private String nombre;

    @NotBlank(message = "El código DANE es obligatorio")
    @Pattern(regexp = "\\d{5}", message = "El código DANE del municipio debe tener exactamente 5 dígitos")
    private String codigoDane;

    @NotNull(message = "El departamento es obligatorio")
    private Long departamentoId;
}
