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
@Table(name = "TBL_LUGAR_PRODUCCION")
public class LugarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_lugar")
    @SequenceGenerator(name = "gen_lugar", sequenceName = "SEQ_LUGAR_PRODUCCION", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "AREA")
    private Double area;

    @Column(name = "VEREDA", length = 150)
    private String vereda;

    @Column(name = "LATITUD")
    private Double latitud;

    @Column(name = "LONGITUD")
    private Double longitud;

    @Column(name = "ALTITUD")
    private Double altitud;

    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MUNICIPIO_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LUGAR_MUNICIPIO"))
    private MunicipioEntity municipio;

    @OneToMany(mappedBy = "lugarProduccion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PredioEntity> predios = new ArrayList<>();
}
