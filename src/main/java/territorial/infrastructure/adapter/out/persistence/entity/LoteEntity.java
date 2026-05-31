package territorial.infrastructure.adapter.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/** Entidad JPA mapeada a la tabla Oracle LOTE. FKs: ID_LUGAR (LugarEntity) e ID_CULTIVO (CultivoEntity). */

@Data
@NoArgsConstructor
@Entity
@Table(name = "LOTE")
public class LoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_lote")
    @SequenceGenerator(name = "gen_lote", sequenceName = "SEQ_LOTE", allocationSize = 1)
    @Column(name = "ID_LOTE")
    private Long id;

    @Column(name = "NOMBRE", length = 200)
    private String nombre;

    @Column(name = "AREA")
    private Double area;

    @Column(name = "FECHA_SIEMBRA")
    private LocalDate fechaSiembra;

    @Column(name = "ESTADO", nullable = false, length = 30)
    private String estado;

    @Column(name = "FECHA_COSECHA_EST")
    private LocalDate fechaCosechaEstimada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LUGAR",
            foreignKey = @ForeignKey(name = "FK_LOTE_LUGAR"))
    private LugarEntity lugarProduccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CULTIVO",
            foreignKey = @ForeignKey(name = "FK_LOTE_CULTIVO"))
    private CultivoEntity cultivo;

    // Fields not in new schema — kept transient for domain mapper compatibility
    @Transient
    private String numero;

    @Transient
    private LocalDate fechaCosechaReal;

    @Transient
    private Double latitud;

    @Transient
    private Double longitud;

    @Transient
    private Double altitud;

    @Transient
    private LocalDateTime fechaCreacion;

    @Transient
    private LocalDateTime fechaActualizacion;

    @Transient
    private List<PlagaEntity> plagas = new ArrayList<>();
}
