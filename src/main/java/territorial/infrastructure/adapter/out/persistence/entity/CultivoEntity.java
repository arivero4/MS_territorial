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
@Table(name = "CULTIVO")
public class CultivoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cultivo")
    @SequenceGenerator(name = "gen_cultivo", sequenceName = "SEQ_CULTIVO", allocationSize = 1)
    @Column(name = "ID_CULTIVO")
    private Long id;

    @Column(name = "NOMBRE_VARIEDAD", nullable = false, length = 200)
    private String nombreVariedad;

    @Column(name = "NOMBRE_CIENTIFICO", length = 200)
    private String nombreCientifico;

    @Column(name = "NOMBRE_COMUN", nullable = false, length = 200)
    private String nombreComun;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    // Fields not in new schema — kept transient for domain mapper compatibility
    @Transient
    private LocalDate fechaInicio;

    @Transient
    private LocalDate fechaEstimadaCosecha;

    @Transient
    private Boolean activo;

    @Transient
    private LocalDateTime fechaCreacion;

    @Transient
    private LocalDateTime fechaActualizacion;

    // Predio FK removed from new schema
    @Transient
    private PredioEntity predio;

    @OneToMany(mappedBy = "cultivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoteEntity> lotes = new ArrayList<>();

    @OneToMany(mappedBy = "cultivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlagaEntity> plagas = new ArrayList<>();
}
