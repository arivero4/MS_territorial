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
                .activo(e.getActivo())
                .fechaCreacion(e.getFechaCreacion())
                .fechaActualizacion(e.getFechaActualizacion())
                .build();
    }

    public DepartamentoEntity toEntity(Departamento d) {
        if (d == null) return null;
        DepartamentoEntity e = new DepartamentoEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setCodigoDane(d.getCodigoDane() != null ? d.getCodigoDane().getCodigo() : null);
        e.setActivo(d.getActivo());
        e.setFechaCreacion(d.getFechaCreacion());
        e.setFechaActualizacion(d.getFechaActualizacion());
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
                .activo(e.getActivo())
                .fechaCreacion(e.getFechaCreacion())
                .fechaActualizacion(e.getFechaActualizacion())
                .build();
    }

    public MunicipioEntity toEntity(Municipio d) {
        if (d == null) return null;
        MunicipioEntity e = new MunicipioEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setCodigoDane(d.getCodigoDane() != null ? d.getCodigoDane().getCodigo() : null);
        e.setActivo(d.getActivo());
        e.setFechaCreacion(d.getFechaCreacion());
        e.setFechaActualizacion(d.getFechaActualizacion());
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
                .descripcion(e.getDescripcion())
                .area(e.getArea())
                .vereda(e.getVereda())
                .coordenadas(buildCoordenadas(e.getLatitud(), e.getLongitud(), e.getAltitud()))
                .municipio(toDomain(e.getMunicipio()))
                .activo(e.getActivo())
                .fechaCreacion(e.getFechaCreacion())
                .fechaActualizacion(e.getFechaActualizacion())
                .build();
    }

    public LugarEntity toEntity(LugarProduccion d) {
        if (d == null) return null;
        LugarEntity e = new LugarEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setDescripcion(d.getDescripcion());
        e.setArea(d.getArea());
        e.setVereda(d.getVereda());
        e.setActivo(d.getActivo());
        e.setFechaCreacion(d.getFechaCreacion());
        e.setFechaActualizacion(d.getFechaActualizacion());
        if (d.getCoordenadas() != null) {
            e.setLatitud(d.getCoordenadas().getLatitud());
            e.setLongitud(d.getCoordenadas().getLongitud());
            e.setAltitud(d.getCoordenadas().getAltitud());
        }
        if (d.getMunicipio() != null) {
            e.setMunicipio(toEntity(d.getMunicipio()));
        }
        return e;
    }

    // ── Predio ────────────────────────────────────────────────────────────────

    public Predio toDomain(PredioEntity e) {
        if (e == null) return null;
        return Predio.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .numeroPredial(e.getNumeroPredial())
                .matriculaInmobiliaria(e.getMatriculaInmobiliaria())
                .area(e.getArea())
                .vereda(e.getVereda())
                .descripcion(e.getDescripcion())
                .coordenadas(buildCoordenadas(e.getLatitud(), e.getLongitud(), e.getAltitud()))
                .lugarProduccion(toDomain(e.getLugarProduccion()))
                .activo(e.getActivo())
                .fechaCreacion(e.getFechaCreacion())
                .fechaActualizacion(e.getFechaActualizacion())
                .build();
    }

    public PredioEntity toEntity(Predio d) {
        if (d == null) return null;
        PredioEntity e = new PredioEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setNumeroPredial(d.getNumeroPredial());
        e.setMatriculaInmobiliaria(d.getMatriculaInmobiliaria());
        e.setArea(d.getArea());
        e.setVereda(d.getVereda());
        e.setDescripcion(d.getDescripcion());
        e.setActivo(d.getActivo());
        e.setFechaCreacion(d.getFechaCreacion());
        e.setFechaActualizacion(d.getFechaActualizacion());
        if (d.getCoordenadas() != null) {
            e.setLatitud(d.getCoordenadas().getLatitud());
            e.setLongitud(d.getCoordenadas().getLongitud());
            e.setAltitud(d.getCoordenadas().getAltitud());
        }
        if (d.getLugarProduccion() != null) {
            e.setLugarProduccion(toEntity(d.getLugarProduccion()));
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
                .fechaInicio(e.getFechaInicio())
                .fechaEstimadaCosecha(e.getFechaEstimadaCosecha())
                .predio(toDomain(e.getPredio()))
                .activo(e.getActivo())
                .fechaCreacion(e.getFechaCreacion())
                .fechaActualizacion(e.getFechaActualizacion())
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
        e.setFechaInicio(d.getFechaInicio());
        e.setFechaEstimadaCosecha(d.getFechaEstimadaCosecha());
        e.setActivo(d.getActivo());
        e.setFechaCreacion(d.getFechaCreacion());
        e.setFechaActualizacion(d.getFechaActualizacion());
        if (d.getPredio() != null) {
            e.setPredio(toEntity(d.getPredio()));
        }
        return e;
    }

    // ── Lote ──────────────────────────────────────────────────────────────────

    public Lote toDomain(LoteEntity e) {
        if (e == null) return null;
        return Lote.builder()
                .id(e.getId())
                .numero(e.getNumero())
                .nombre(e.getNombre())
                .area(e.getArea())
                .estado(e.getEstado() != null ? EstadoLote.valueOf(e.getEstado()) : null)
                .fechaSiembra(e.getFechaSiembra())
                .fechaCosechaEstimada(e.getFechaCosechaEstimada())
                .fechaCosechaReal(e.getFechaCosechaReal())
                .coordenadas(buildCoordenadas(e.getLatitud(), e.getLongitud(), e.getAltitud()))
                .cultivo(toDomain(e.getCultivo()))
                .fechaCreacion(e.getFechaCreacion())
                .fechaActualizacion(e.getFechaActualizacion())
                .build();
    }

    public LoteEntity toEntity(Lote d) {
        if (d == null) return null;
        LoteEntity e = new LoteEntity();
        e.setId(d.getId());
        e.setNumero(d.getNumero());
        e.setNombre(d.getNombre());
        e.setArea(d.getArea());
        e.setEstado(d.getEstado() != null ? d.getEstado().name() : null);
        e.setFechaSiembra(d.getFechaSiembra());
        e.setFechaCosechaEstimada(d.getFechaCosechaEstimada());
        e.setFechaCosechaReal(d.getFechaCosechaReal());
        e.setFechaCreacion(d.getFechaCreacion());
        e.setFechaActualizacion(d.getFechaActualizacion());
        if (d.getCoordenadas() != null) {
            e.setLatitud(d.getCoordenadas().getLatitud());
            e.setLongitud(d.getCoordenadas().getLongitud());
            e.setAltitud(d.getCoordenadas().getAltitud());
        }
        if (d.getCultivo() != null) {
            e.setCultivo(toEntity(d.getCultivo()));
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
                .descripcion(e.getDescripcion())
                .tipo(e.getTipo())
                .nivelRiesgo(e.getNivelRiesgo())
                .sintomas(e.getSintomas())
                .tratamiento(e.getTratamiento())
                .activo(e.getActivo())
                .fechaCreacion(e.getFechaCreacion())
                .fechaActualizacion(e.getFechaActualizacion())
                .build();
    }

    public PlagaEntity toEntity(Plaga d) {
        if (d == null) return null;
        PlagaEntity e = new PlagaEntity();
        e.setId(d.getId());
        e.setNombreCientifico(d.getNombreCientifico());
        e.setNombreComun(d.getNombreComun());
        e.setDescripcion(d.getDescripcion());
        e.setTipo(d.getTipo());
        e.setNivelRiesgo(d.getNivelRiesgo());
        e.setSintomas(d.getSintomas());
        e.setTratamiento(d.getTratamiento());
        e.setActivo(d.getActivo());
        e.setFechaCreacion(d.getFechaCreacion());
        e.setFechaActualizacion(d.getFechaActualizacion());
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
