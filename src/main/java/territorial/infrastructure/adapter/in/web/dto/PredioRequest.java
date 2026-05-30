package territorial.infrastructure.adapter.in.web.dto;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Diccionario: predio (id_predio, nombre, num_predial, area, vereda, latitud, longitud,
 *                      id_privilegio_grupo, id_municipio, id_lugar_produccion)
 */
@Data
public class PredioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede superar 200 caracteres")
    private String nombre;

    @NotBlank(message = "El número predial es obligatorio")
    @Size(max = 50, message = "El número predial no puede superar 50 caracteres")
    private String numeroPredial;

    @NotNull(message = "El área es obligatoria")
    @Positive(message = "El área debe ser un valor positivo")
    private Double area;

    @NotBlank(message = "La vereda es obligatoria")
    @Size(max = 150, message = "La vereda no puede superar 150 caracteres")
    private String vereda;

    private Double latitud;

    private Double longitud;

    // Referencia externa al privilegio de grupo
    private Long idPrivilegioGrupo;

    @NotNull(message = "El municipio es obligatorio")
    private Long municipioId;

    @NotNull(message = "El lugar de producción es obligatorio")
    private Long lugarProduccionId;
}
