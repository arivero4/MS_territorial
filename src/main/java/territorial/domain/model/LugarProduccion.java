package territorial.domain.model;

import territorial.domain.valueobject.Coordenadas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un Lugar de Producción (finca/unidad productiva).
 *
 * <p>Unidad funcional de producción agrícola. Contiene uno o varios {@link Lote}s
 * con cultivos hortifrutícolas sujetos a inspección fitosanitaria.</p>
 *
 * <p>Se vincula a un {@link Predio} a través de la FK {@code ID_LUGAR_PRODUCCION}
 * de la tabla {@code PREDIO}. <strong>No</strong> tiene referencia directa
 * a municipio; ese dato se obtiene navegando: Predio → Municipio.</p>
 *
 * <p>Jerarquía: Municipio → Predio → <strong>LugarProduccion</strong>
 * → Lote → Cultivo → Plaga.</p>
 *
 * <p>Tabla Oracle: {@code LUGAR_PRODUCCION}.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LugarProduccion {

    /** Identificador único. Asignado por {@code SEQ_LUGAR_PRODUCCION}. */
    private Long id;

    /** Nombre del lugar de producción (ej.: "Finca La Esperanza"). */
    private String nombre;

    /** Descripción opcional de las características del lugar. */
    private String descripcion;

    /** Área total del lugar de producción expresada en hectáreas. */
    private Double area;

    /** Vereda o sector rural donde se ubica el lugar. */
    private String vereda;

    /**
     * Coordenadas geográficas del lugar (latitud, longitud, altitud opcional).
     * Encapsuladas en el value object {@link Coordenadas}.
     */
    private Coordenadas coordenadas;

    /** {@code true} si el lugar está operativo y puede recibir inspecciones. */
    private Boolean activo;

    /** Fecha de creación del registro. */
    private LocalDateTime fechaCreacion;

    /** Fecha de última modificación. */
    private LocalDateTime fechaActualizacion;

    /**
     * Indica si el lugar de producción está habilitado para operar.
     *
     * @return {@code true} cuando {@code activo} es {@code Boolean.TRUE}.
     */
    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Verifica que las coordenadas contienen latitud y longitud válidas.
     *
     * @return {@code true} si {@link Coordenadas} no es null y tiene lat/lon definidos.
     */
    public boolean tieneCoordenadasValidas() {
        return coordenadas != null
                && coordenadas.getLatitud() != null
                && coordenadas.getLongitud() != null;
    }

    /**
     * Activa el lugar de producción y actualiza la fecha de modificación.
     */
    public void activar() {
        this.activo = true;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Desactiva el lugar de producción y actualiza la fecha de modificación.
     */
    public void desactivar() {
        this.activo = false;
        this.fechaActualizacion = LocalDateTime.now();
    }
}
