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
@Table(name = "TBL_CULTIVO")
public class CultivoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cultivo")
    @SequenceGenerator(name = "gen_cultivo", sequenceName = "SEQ_CULTIVO", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE_VARIEDAD", nullable = false, length = 200)
    private String nombreVariedad;

    @Column(name = "NOMBRE_CIENTIFICO", length = 200)
    private String nombreCientifico;

    @Column(name = "NOMBRE_COMUN", nullable = false, length = 200)
    private String nombreComun;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "FECHA_INICIO")
    private LocalDate fechaInicio;

    @Column(name = "FECHA_ESTIMADA_COSECHA")
    private LocalDate fechaEstimadaCosecha;

    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREDIO_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_CULTIVO_PREDIO"))
    private PredioEntity predio;

    @OneToMany(mappedBy = "cultivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoteEntity> lotes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TBL_CULTIVO_PLAGA",
            joinColumns = @JoinColumn(name = "CULTIVO_ID", foreignKey = @ForeignKey(name = "FK_CP_CULTIVO")),
            inverseJoinColumns = @JoinColumn(name = "PLAGA_ID", foreignKey = @ForeignKey(name = "FK_CP_PLAGA"))
    )
    private List<PlagaEntity> plagas = new ArrayList<>();
}
