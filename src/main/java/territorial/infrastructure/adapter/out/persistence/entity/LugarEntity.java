package territorial.infrastructure.adapter.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "LUGAR_PRODUCCION")
public class LugarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_lugar")
    @SequenceGenerator(name = "gen_lugar", sequenceName = "SEQ_LUGAR_PRODUCCION", allocationSize = 1)
    @Column(name = "ID_LUGAR")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "AREA", nullable = false)
    private Double area;

    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;

    @Column(name = "ID_PRIVILEGIO_GRUPO", nullable = false)
    private Long idPrivilegioGrupo;

    // Fields not present in new schema — kept transient for domain mapper compatibility
    @Transient
    private String descripcion;

    @Transient
    private String vereda;

    @Transient
    private Double latitud;

    @Transient
    private Double longitud;

    @Transient
    private Double altitud;

    @Transient
    private Boolean activo;

    @Transient
    private LocalDateTime fechaCreacion;

    @Transient
    private LocalDateTime fechaActualizacion;

    // Municipio relationship removed from new schema
    @Transient
    private MunicipioEntity municipio;

    @OneToMany(mappedBy = "lugarProduccion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoteEntity> lotes = new ArrayList<>();
}
