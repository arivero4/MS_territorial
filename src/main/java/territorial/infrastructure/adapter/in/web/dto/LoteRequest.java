package territorial.infrastructure.adapter.in.web.dto;

import territorial.domain.enums.EstadoLote;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Diccionario: lote (id_lote, nombre, area, fecha_siembra, estado, fecha_cosecha_est, id_lugar, id_cultivo)
 */
@Data
public class LoteRequest {

    @NotBlank(message = "El nombre del lote es obligatorio")
    @Size(max = 200, message = "El nombre no puede superar 200 caracteres")
    private String nombre;

    @NotNull(message = "El área es obligatoria")
    @Positive(message = "El área debe ser un valor positivo")
    private Double area;

    private EstadoLote estado;

    @NotNull(message = "La fecha de siembra es obligatoria")
    private LocalDate fechaSiembra;

    @NotNull(message = "La fecha estimada de cosecha es obligatoria")
    private LocalDate fechaCosechaEstimada;

    @NotNull(message = "El lugar de producción es obligatorio")
    private Long idLugar;

    @NotNull(message = "El cultivo es obligatorio")
    private Long cultivoId;
}
