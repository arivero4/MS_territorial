package territorial.infrastructure.adapter.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/** Entidad JPA mapeada a la tabla Oracle PREDIO. FKs: ID_MUNICIPIO e ID_LUGAR_PRODUCCION. */

@Data
@NoArgsConstructor
@Entity
@Table(name = "PREDIO")
public class PredioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_predio")
    @SequenceGenerator(name = "gen_predio", sequenceName = "SEQ_PREDIO", allocationSize = 1)
    @Column(name = "ID_PREDIO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "NUM_PREDIAL", length = 30)
    private String numeroPredial;

    @Column(name = "AREA")
    private Double area;

    @Column(name = "VEREDA", length = 150)
    private String vereda;

    @Column(name = "LATITUD")
    private Double latitud;

    @Column(name = "LONGITUD")
    private Double longitud;

    @Column(name = "ID_PRIVILEGIO_GRUPO")
    private Long idPrivilegioGrupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MUNICIPIO",
            foreignKey = @ForeignKey(name = "FK_PREDIO_MUNICIPIO"))
    private MunicipioEntity municipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LUGAR_PRODUCCION",
            foreignKey = @ForeignKey(name = "FK_PREDIO_LUGAR"))
    private LugarEntity lugarProduccion;

    // Fields not in new schema — kept transient for domain mapper compatibility
    @Transient
    private String matriculaInmobiliaria;

    @Transient
    private String descripcion;

    @Transient
    private Double altitud;

    @Transient
    private Boolean activo;

    @Transient
    private LocalDateTime fechaCreacion;

    @Transient
    private LocalDateTime fechaActualizacion;

    // Cultivos no longer linked via PREDIO FK in new schema
    @Transient
    private List<CultivoEntity> cultivos = new ArrayList<>();
}
