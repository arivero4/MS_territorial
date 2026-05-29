package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class LugarRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede superar 200 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;

    @Positive(message = "El área debe ser un valor positivo")
    private Double area;

    @Size(max = 150, message = "La vereda no puede superar 150 caracteres")
    private String vereda;

    private Double latitud;

    private Double longitud;

    private Double altitud;

    @NotNull(message = "El municipio es obligatorio")
    private Long municipioId;
}
