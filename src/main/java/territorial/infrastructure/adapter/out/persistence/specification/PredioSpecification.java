package territorial.infrastructure.adapter.out.persistence.specification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PredioSpecification {

    private String nombre;
    private String numeroPredial;
    private Long lugarProduccionId;
    private Long municipioId;
    private Long departamentoId;
    private Double areaMinima;
    private Double areaMaxima;
    private Boolean activo;
    private String vereda;

    public String buildJpqlWhere() {
        StringBuilder sb = new StringBuilder("WHERE 1=1");
        if (nombre != null && !nombre.isBlank()) {
            sb.append(" AND UPPER(p.nombre) LIKE UPPER(:nombre)");
        }
        if (numeroPredial != null && !numeroPredial.isBlank()) {
            sb.append(" AND p.numeroPredial = :numeroPredial");
        }
        if (lugarProduccionId != null) {
            sb.append(" AND p.lugarProduccion.id = :lugarProduccionId");
        }
        if (municipioId != null) {
            // Predio now has direct municipio FK in new schema
            sb.append(" AND p.municipio.id = :municipioId");
        }
        if (departamentoId != null) {
            sb.append(" AND p.municipio.departamento.id = :departamentoId");
        }
        if (areaMinima != null) {
            sb.append(" AND p.area >= :areaMinima");
        }
        if (areaMaxima != null) {
            sb.append(" AND p.area <= :areaMaxima");
        }
        // activo removed from new schema — skip filter
        if (vereda != null && !vereda.isBlank()) {
            sb.append(" AND UPPER(p.vereda) LIKE UPPER(:vereda)");
        }
        return sb.toString();
    }
}
