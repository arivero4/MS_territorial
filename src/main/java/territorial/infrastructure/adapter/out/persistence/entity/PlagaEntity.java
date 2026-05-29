package territorial.infrastructure.adapter.out.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TBL_PLAGA")
public class PlagaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_plaga")
    @SequenceGenerator(name = "gen_plaga", sequenceName = "SEQ_PLAGA", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE_CIENTIFICO", length = 200)
    private String nombreCientifico;

    @Column(name = "NOMBRE_COMUN", nullable = false, length = 200)
    private String nombreComun;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "TIPO", length = 100)
    private String tipo;

    @Column(name = "NIVEL_RIESGO", length = 20)
    private String nivelRiesgo;

    @Column(name = "SINTOMAS", length = 1000)
    private String sintomas;

    @Column(name = "TRATAMIENTO", length = 1000)
    private String tratamiento;

    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;
}
