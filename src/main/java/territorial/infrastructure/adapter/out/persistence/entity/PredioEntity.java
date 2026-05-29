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
@Table(name = "TBL_PREDIO")
public class PredioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_predio")
    @SequenceGenerator(name = "gen_predio", sequenceName = "SEQ_PREDIO", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "NUMERO_PREDIAL", length = 30)
    private String numeroPredial;

    @Column(name = "MATRICULA_INMOBILIARIA", length = 50)
    private String matriculaInmobiliaria;

    @Column(name = "AREA")
    private Double area;

    @Column(name = "VEREDA", length = 150)
    private String vereda;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

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
    @JoinColumn(name = "LUGAR_PRODUCCION_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PREDIO_LUGAR"))
    private LugarEntity lugarProduccion;

    @OneToMany(mappedBy = "predio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CultivoEntity> cultivos = new ArrayList<>();
}
