package territorial.infrastructure.adapter.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PLAGA")
public class PlagaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_plaga")
    @SequenceGenerator(name = "gen_plaga", sequenceName = "SEQ_PLAGA", allocationSize = 1)
    @Column(name = "ID_PLAGA")
    private Long id;

    @Column(name = "NOMBRE_CIENTIFICO", length = 200)
    private String nombreCientifico;

    @Column(name = "NOMBRE_COMUN", nullable = false, length = 200)
    private String nombreComun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CULTIVO",
            foreignKey = @ForeignKey(name = "FK_PLAGA_CULTIVO"))
    private CultivoEntity cultivo;

    // Fields not in new schema — kept transient for domain mapper compatibility
    @Transient
    private String descripcion;

    @Transient
    private String tipo;

    @Transient
    private String nivelRiesgo;

    @Transient
    private String sintomas;

    @Transient
    private String tratamiento;

    @Transient
    private Boolean activo;

    @Transient
    private LocalDateTime fechaCreacion;

    @Transient
    private LocalDateTime fechaActualizacion;
}
