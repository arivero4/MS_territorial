package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PredioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede superar 200 caracteres")
    private String nombre;

    @Size(max = 30, message = "El número predial no puede superar 30 caracteres")
    private String numeroPredial;

    @Size(max = 50, message = "La matrícula inmobiliaria no puede superar 50 caracteres")
    private String matriculaInmobiliaria;

    @Positive(message = "El área debe ser un valor positivo")
    private Double area;

    @Size(max = 150, message = "La vereda no puede superar 150 caracteres")
    private String vereda;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;

    private Double latitud;

    private Double longitud;

    private Double altitud;

    @NotNull(message = "El lugar de producción es obligatorio")
    private Long lugarProduccionId;
}
