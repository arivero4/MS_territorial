package territorial.domain.model;

import territorial.domain.valueobject.Coordenadas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de dominio que representa un Predio rural.
 *
 * <p>Unidad jurídica de tierra registrada. Conecta la estructura geográfica
 * (Municipio) con la unidad productiva (LugarProduccion). Un predio puede tener
 * uno o varios {@link LugarProduccion} dentro de él.</p>
 *
 * <p>Jerarquía: Municipio → <strong>Predio</strong> → LugarProduccion
 * → Lote → Cultivo → Plaga.</p>
 *
 * <p>Tabla Oracle: {@code PREDIO} con FKs {@code ID_MUNICIPIO}
 * e {@code ID_LUGAR_PRODUCCION}. <strong>No</strong> tiene cultivos directos;
 * los cultivos se vinculan a través de los lotes.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Predio {

    /** Identificador único. Asignado por la secuencia Oracle {@code SEQ_PREDIO}. */
    private Long id;

    /** Nombre descriptivo del predio (ej.: "Predio El Carmelo"). */
    private String nombre;

    /**
     * Número predial catastral asignado por la oficina de catastro.
     * Debe ser único en el sistema (se valida en {@link PredioRepositoryPort}).
     */
    private String numeroPredial;

    /**
     * Número de matrícula inmobiliaria del predio en el registro de instrumentos públicos.
     * Campo opcional; verificable con {@link #tieneMatriculaInmobiliaria()}.
     */
    private String matriculaInmobiliaria;

    /** Área total del predio en hectáreas. */
    private Double area;

    /** Nombre de la vereda o sector rural donde se ubica el predio. */
    private String vereda;

    /** Descripción adicional del predio (condiciones del suelo, tipo de terreno, etc.). */
    private String descripcion;

    /**
     * Coordenadas geográficas del predio (lat/lon).
     * Encapsuladas en el value object {@link Coordenadas}.
     */
    private Coordenadas coordenadas;

    /**
     * Lugar de producción al que pertenece este predio.
     * Relación N:1 (muchos predios pueden asociarse a un lugar).
     * FK en tabla: {@code ID_LUGAR_PRODUCCION}.
     */
    private LugarProduccion lugarProduccion;

    /**
     * Municipio donde está ubicado el predio.
     * Relación N:1; proporciona la cadena de ubicación geográfica completa.
     * FK en tabla: {@code ID_MUNICIPIO}.
     */
    private Municipio municipio;

    /** {@code true} si el predio está habilitado para operaciones. */
    private Boolean activo;

    /** Fecha de creación del registro. */
    private LocalDateTime fechaCreacion;

    /** Fecha de última modificación. */
    private LocalDateTime fechaActualizacion;

    /**
     * Indica si el predio está habilitado en el sistema.
     *
     * @return {@code true} cuando {@code activo} es {@code Boolean.TRUE}.
     */
    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Devuelve el ID del lugar de producción, o {@code null} si no tiene.
     *
     * @return ID del lugar de producción o {@code null}.
     */
    public Long getLugarProduccionId() {
        return lugarProduccion != null ? lugarProduccion.getId() : null;
    }

    /**
     * Devuelve el ID del municipio del predio, o {@code null} si no tiene.
     *
     * @return ID del municipio o {@code null}.
     */
    public Long getMunicipioId() {
        return municipio != null ? municipio.getId() : null;
    }

    /**
     * Verifica si el predio tiene matrícula inmobiliaria registrada.
     *
     * @return {@code true} si {@code matriculaInmobiliaria} no es null ni vacía.
     */
    public boolean tieneMatriculaInmobiliaria() {
        return matriculaInmobiliaria != null && !matriculaInmobiliaria.isBlank();
    }

    /**
     * Habilita el predio y actualiza la fecha de modificación.
     */
    public void activar() {
        this.activo = true;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Deshabilita el predio y actualiza la fecha de modificación.
     */
    public void desactivar() {
        this.activo = false;
        this.fechaActualizacion = LocalDateTime.now();
    }
}
