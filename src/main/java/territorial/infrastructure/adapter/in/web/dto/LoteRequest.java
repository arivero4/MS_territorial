package territorial.infrastructure.adapter.in.web.dto;

import territorial.domain.enums.EstadoLote;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class LoteRequest {

    @NotBlank(message = "El número de lote es obligatorio")
    @Size(max = 50, message = "El número no puede superar 50 caracteres")
    private String numero;

    @Size(max = 200, message = "El nombre no puede superar 200 caracteres")
    private String nombre;

    @Positive(message = "El área debe ser un valor positivo")
    private Double area;

    private EstadoLote estado;

    private LocalDate fechaSiembra;

    private LocalDate fechaCosechaEstimada;

    private Double latitud;

    private Double longitud;

    private Double altitud;

    @NotNull(message = "El cultivo es obligatorio")
    private Long cultivoId;
}
