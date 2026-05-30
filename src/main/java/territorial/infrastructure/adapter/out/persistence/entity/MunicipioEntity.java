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
@Table(name = "MUNICIPIO",
        uniqueConstraints = @UniqueConstraint(name = "UK_MUNI_CODIGO_DANE", columnNames = "CODIGO_DANE"))
public class MunicipioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_municipio")
    @SequenceGenerator(name = "gen_municipio", sequenceName = "SEQ_MUNICIPIO", allocationSize = 1)
    @Column(name = "ID_MUNICIPIO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "CODIGO_DANE", nullable = false, length = 10)
    private String codigoDane;

    // Kept for backward compatibility with domain mapper; not in new schema
    @Transient
    private Boolean activo;

    @Transient
    private LocalDateTime fechaCreacion;

    @Transient
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DEPARTAMENTO", nullable = false,
            foreignKey = @ForeignKey(name = "FK_MUNICIPIO_DEPARTAMENTO"))
    private DepartamentoEntity departamento;

    @OneToMany(mappedBy = "municipio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PredioEntity> predios = new ArrayList<>();
}
