package territorial.infrastructure.adapter.out.persistence.mapper;

import territorial.domain.enums.EstadoLote;
import territorial.domain.model.*;
import territorial.domain.valueobject.CodigoDane;
import territorial.domain.valueobject.Coordenadas;
import territorial.infrastructure.adapter.out.persistence.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper exclusivo de la capa de persistencia.
 * Solo convierte entre objetos de dominio y entidades JPA.
 * No conoce nada de la capa web (DTOs, requests, responses).
 */
@Component
public class TerritorialEntityMapper {

    // ── Departamento ─────────────────────────────────────────────────────────

    public Departamento toDomain(DepartamentoEntity e) {
        if (e == null) return null;
        return Departamento.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .codigoDane(e.getCodigoDane() != null ? CodigoDane.deSinValidar(e.getCodigoDane()) : null)
                .activo(true)
                .build();
    }

    public DepartamentoEntity toEntity(Departamento d) {
        if (d == null) return null;
        DepartamentoEntity e = new DepartamentoEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setCodigoDane(d.getCodigoDane() != null ? d.getCodigoDane().getCodigo() : null);
        return e;
    }

    // ── Municipio ─────────────────────────────────────────────────────────────

    public Municipio toDomain(MunicipioEntity e) {
        if (e == null) return null;
        return Municipio.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .codigoDane(e.getCodigoDane() != null ? CodigoDane.deSinValidar(e.getCodigoDane()) : null)
                .departamento(toDomain(e.getDepartamento()))
                .activo(true)
                .build();
    }

    public MunicipioEntity toEntity(Municipio d) {
        if (d == null) return null;
        MunicipioEntity e = new MunicipioEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setCodigoDane(d.getCodigoDane() != null ? d.getCodigoDane().getCodigo() : null);
        if (d.getDepartamento() != null) {
            e.setDepartamento(toEntity(d.getDepartamento()));
        }
        return e;
    }

    // ── LugarProduccion ───────────────────────────────────────────────────────

    public LugarProduccion toDomain(LugarEntity e) {
        if (e == null) return null;
        return LugarProduccion.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .area(e.getArea())
                .activo(e.getEstado() != null && !"INACTIVO".equalsIgnoreCase(e.getEstado()))
                .build();
    }

    public LugarEntity toEntity(LugarProduccion d) {
        if (d == null) return null;
        LugarEntity e = new LugarEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setArea(d.getArea() != null ? d.getArea() : 0.0);
        e.setEstado(Boolean.FALSE.equals(d.getActivo()) ? "INACTIVO" : "ACTIVO");
        // id_privilegio_grupo defaults to 1 if not set (required NOT NULL)
        e.setIdPrivilegioGrupo(1L);
        return e;
    }

    // ── Predio ────────────────────────────────────────────────────────────────

    public Predio toDomain(PredioEntity e) {
        if (e == null) return null;
        return Predio.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .numeroPredial(e.getNumeroPredial())
                .area(e.getArea())
                .vereda(e.getVereda())
                .coordenadas(buildCoordenadas(e.getLatitud(), e.getLongitud(), null))
                .lugarProduccion(toDomain(e.getLugarProduccion()))
                .activo(true)
                .build();
    }

    public PredioEntity toEntity(Predio d) {
        if (d == null) return null;
        PredioEntity e = new PredioEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setNumeroPredial(d.getNumeroPredial());
        e.setArea(d.getArea());
        e.setVereda(d.getVereda());
        // Latitud/longitud NOT NULL in new schema - default 0 if not provided
        if (d.getCoordenadas() != null) {
            e.setLatitud(d.getCoordenadas().getLatitud() != null ? d.getCoordenadas().getLatitud() : 0.0);
            e.setLongitud(d.getCoordenadas().getLongitud() != null ? d.getCoordenadas().getLongitud() : 0.0);
        } else {
            e.setLatitud(0.0);
            e.setLongitud(0.0);
        }
        e.setIdPrivilegioGrupo(1L);
        if (d.getLugarProduccion() != null) {
            e.setLugarProduccion(toEntity(d.getLugarProduccion()));
        }
        // Map id_municipio FK (diccionario: predio.id_municipio required)
        if (d.getIdMunicipio() != null) {
            MunicipioEntity m = new MunicipioEntity();
            m.setId(d.getIdMunicipio());
            e.setMunicipio(m);
        }
        return e;
    }

    // ── Cultivo ───────────────────────────────────────────────────────────────

    public Cultivo toDomain(CultivoEntity e) {
        if (e == null) return null;
        return Cultivo.builder()
                .id(e.getId())
                .nombreVariedad(e.getNombreVariedad())
                .nombreCientifico(e.getNombreCientifico())
                .nombreComun(e.getNombreComun())
                .descripcion(e.getDescripcion())
                .activo(true)
                .build();
    }

    public CultivoEntity toEntity(Cultivo d) {
        if (d == null) return null;
        CultivoEntity e = new CultivoEntity();
        e.setId(d.getId());
        e.setNombreVariedad(d.getNombreVariedad());
        e.setNombreCientifico(d.getNombreCientifico());
        e.setNombreComun(d.getNombreComun());
        e.setDescripcion(d.getDescripcion());
        return e;
    }

    // ── Lote ──────────────────────────────────────────────────────────────────

    public Lote toDomain(LoteEntity e) {
        if (e == null) return null;
        Lote.LoteBuilder b = Lote.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .area(e.getArea())
                .estado(e.getEstado() != null ? EstadoLote.valueOf(e.getEstado()) : null)
                .fechaSiembra(e.getFechaSiembra())
                .fechaCosechaEstimada(e.getFechaCosechaEstimada())
                .cultivo(toDomain(e.getCultivo()));
        if (e.getLugarProduccion() != null) {
            b.lugarProduccion(toDomain(e.getLugarProduccion()))
             .idLugar(e.getLugarProduccion().getId());
        }
        return b.build();
    }

    public LoteEntity toEntity(Lote d) {
        if (d == null) return null;
        LoteEntity e = new LoteEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setArea(d.getArea());
        e.setEstado(d.getEstado() != null ? d.getEstado().name() : "ACTIVO");
        e.setFechaSiembra(d.getFechaSiembra());
        e.setFechaCosechaEstimada(d.getFechaCosechaEstimada());
        if (d.getCultivo() != null) {
            e.setCultivo(toEntity(d.getCultivo()));
        }
        // Map id_lugar FK (diccionario: lote.id_lugar required)
        if (d.getLugarProduccion() != null) {
            e.setLugarProduccion(toEntity(d.getLugarProduccion()));
        } else if (d.getIdLugar() != null) {
            LugarEntity l = new LugarEntity();
            l.setId(d.getIdLugar());
            l.setNombre("");
            l.setArea(0.0);
            l.setEstado("ACTIVO");
            l.setIdPrivilegioGrupo(1L);
            e.setLugarProduccion(l);
        }
        return e;
    }

    // ── Plaga ─────────────────────────────────────────────────────────────────

    public Plaga toDomain(PlagaEntity e) {
        if (e == null) return null;
        return Plaga.builder()
                .id(e.getId())
                .nombreCientifico(e.getNombreCientifico())
                .nombreComun(e.getNombreComun())
                .activo(true)
                .build();
    }

    public PlagaEntity toEntity(Plaga d) {
        if (d == null) return null;
        PlagaEntity e = new PlagaEntity();
        e.setId(d.getId());
        e.setNombreCientifico(d.getNombreCientifico());
        e.setNombreComun(d.getNombreComun());
        // Map id_cultivo FK (diccionario: plaga.id_cultivo required)
        if (d.getIdCultivo() != null) {
            CultivoEntity c = new CultivoEntity();
            c.setId(d.getIdCultivo());
            e.setCultivo(c);
        }
        return e;
    }

    // ── List helpers ──────────────────────────────────────────────────────────

    public List<Departamento> toDepartamentoDomainList(List<DepartamentoEntity> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toDomain).collect(Collectors.toList());
    }

    public List<Municipio> toMunicipioDomainList(List<MunicipioEntity> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toDomain).collect(Collectors.toList());
    }

    public List<LugarProduccion> toLugarDomainList(List<LugarEntity> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toDomain).collect(Collectors.toList());
    }

    public List<Predio> toPredioDomainList(List<PredioEntity> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toDomain).collect(Collectors.toList());
    }

    public List<Cultivo> toCultivoDomainList(List<CultivoEntity> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toDomain).collect(Collectors.toList());
    }

    public List<Lote> toLoteDomainList(List<LoteEntity> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toDomain).collect(Collectors.toList());
    }

    public List<Plaga> toPlagaDomainList(List<PlagaEntity> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toDomain).collect(Collectors.toList());
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private Coordenadas buildCoordenadas(Double lat, Double lon, Double alt) {
        if (lat == null || lon == null) return null;
        return Coordenadas.de(lat, lon, alt);
    }
}
