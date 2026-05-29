package territorial.infrastructure.adapter.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TBL_LOTE")
public class LoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_lote")
    @SequenceGenerator(name = "gen_lote", sequenceName = "SEQ_LOTE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMERO", nullable = false, length = 50)
    private String numero;

    @Column(name = "NOMBRE", length = 200)
    private String nombre;

    @Column(name = "AREA")
    private Double area;

    @Column(name = "ESTADO", nullable = false, length = 30)
    private String estado;

    @Column(name = "FECHA_SIEMBRA")
    private LocalDate fechaSiembra;

    @Column(name = "FECHA_COSECHA_ESTIMADA")
    private LocalDate fechaCosechaEstimada;

    @Column(name = "FECHA_COSECHA_REAL")
    private LocalDate fechaCosechaReal;

    @Column(name = "LATITUD")
    private Double latitud;

    @Column(name = "LONGITUD")
    private Double longitud;

    @Column(name = "ALTITUD")
    private Double altitud;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CULTIVO_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LOTE_CULTIVO"))
    private CultivoEntity cultivo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TBL_LOTE_PLAGA",
            joinColumns = @JoinColumn(name = "LOTE_ID", foreignKey = @ForeignKey(name = "FK_LP_LOTE")),
            inverseJoinColumns = @JoinColumn(name = "PLAGA_ID", foreignKey = @ForeignKey(name = "FK_LP_PLAGA"))
    )
    private List<PlagaEntity> plagas = new ArrayList<>();
}
