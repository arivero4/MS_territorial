package territorial.infrastructure.adapter.in.web.mapper;

import territorial.domain.model.*;
import territorial.domain.valueobject.CodigoDane;
import territorial.domain.valueobject.Coordenadas;
import territorial.infrastructure.adapter.in.web.dto.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper exclusivo de la capa web (adaptador de entrada).
 * Convierte entre objetos de dominio y DTOs (requests/responses HTTP).
 * No conoce nada de la capa de persistencia (entities, JPA).
 */
@Component
public class TerritorialWebMapper {

    // ── Departamento ─────────────────────────────────────────────────────────

    public Departamento toDomain(DepartamentoRequest req) {
        if (req == null) return null;
        Departamento d = new Departamento();
        d.setNombre(req.getNombre());
        d.setCodigoDane(CodigoDane.de(req.getCodigoDane()));
        return d;
    }

    public DepartamentoResponse toResponse(Departamento d) {
        if (d == null) return null;
        return DepartamentoResponse.builder()
                .id(d.getId())
                .nombre(d.getNombre())
                .codigoDane(d.getCodigoDane() != null ? d.getCodigoDane().getCodigo() : null)
                .activo(d.getActivo())
                .fechaCreacion(d.getFechaCreacion())
                .fechaActualizacion(d.getFechaActualizacion())
                .build();
    }

    public List<DepartamentoResponse> toDepartamentoResponseList(List<Departamento> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Municipio ─────────────────────────────────────────────────────────────

    public Municipio toDomain(MunicipioRequest req) {
        if (req == null) return null;
        Municipio m = new Municipio();
        m.setNombre(req.getNombre());
        m.setCodigoDane(CodigoDane.de(req.getCodigoDane()));
        if (req.getDepartamentoId() != null) {
            Departamento d = new Departamento();
            d.setId(req.getDepartamentoId());
            m.setDepartamento(d);
        }
        return m;
    }

    public MunicipioResponse toResponse(Municipio m) {
        if (m == null) return null;
        MunicipioResponse.MunicipioResponseBuilder builder = MunicipioResponse.builder()
                .id(m.getId())
                .nombre(m.getNombre())
                .codigoDane(m.getCodigoDane() != null ? m.getCodigoDane().getCodigo() : null)
                .activo(m.getActivo())
                .fechaCreacion(m.getFechaCreacion())
                .fechaActualizacion(m.getFechaActualizacion());
        if (m.getDepartamento() != null) {
            builder.departamentoId(m.getDepartamento().getId())
                   .departamentoNombre(m.getDepartamento().getNombre())
                   .departamentoCodigoDane(m.getDepartamento().getCodigoDane() != null
                           ? m.getDepartamento().getCodigoDane().getCodigo() : null);
        }
        return builder.build();
    }

    public List<MunicipioResponse> toMunicipioResponseList(List<Municipio> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── LugarProduccion ───────────────────────────────────────────────────────

    public LugarProduccion toDomain(LugarRequest req) {
        if (req == null) return null;
        LugarProduccion d = new LugarProduccion();
        d.setNombre(req.getNombre());
        d.setArea(req.getArea());
        // estado se maneja como 'activo' en dominio
        if (req.getEstado() != null) {
            d.setActivo("ACTIVO".equalsIgnoreCase(req.getEstado()));
        }
        return d;
    }

    public LugarResponse toResponse(LugarProduccion d) {
        if (d == null) return null;
        LugarResponse r = new LugarResponse();
        r.setId(d.getId());
        r.setNombre(d.getNombre());
        r.setDescripcion(d.getDescripcion());
        r.setArea(d.getArea());
        r.setVereda(d.getVereda());
        r.setActivo(d.getActivo());
        r.setFechaCreacion(d.getFechaCreacion());
        r.setFechaActualizacion(d.getFechaActualizacion());
        if (d.getCoordenadas() != null) {
            r.setLatitud(d.getCoordenadas().getLatitud());
            r.setLongitud(d.getCoordenadas().getLongitud());
            r.setAltitud(d.getCoordenadas().getAltitud());
        }
        if (d.getMunicipio() != null) {
            r.setMunicipioId(d.getMunicipio().getId());
            r.setMunicipioNombre(d.getMunicipio().getNombre());
            if (d.getMunicipio().getDepartamento() != null) {
                r.setDepartamentoId(d.getMunicipio().getDepartamento().getId());
                r.setDepartamentoNombre(d.getMunicipio().getDepartamento().getNombre());
            }
        }
        return r;
    }

    public List<LugarResponse> toLugarResponseList(List<LugarProduccion> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Predio ────────────────────────────────────────────────────────────────

    public Predio toDomain(PredioRequest req) {
        if (req == null) return null;
        Predio d = new Predio();
        d.setNombre(req.getNombre());
        d.setNumeroPredial(req.getNumeroPredial());
        d.setArea(req.getArea());
        d.setVereda(req.getVereda());
        if (req.getLatitud() != null && req.getLongitud() != null) {
            d.setCoordenadas(Coordenadas.de(req.getLatitud(), req.getLongitud(), null));
        }
        if (req.getLugarProduccionId() != null) {
            LugarProduccion l = new LugarProduccion();
            l.setId(req.getLugarProduccionId());
            d.setLugarProduccion(l);
        }
        if (req.getMunicipioId() != null) {
            d.setIdMunicipio(req.getMunicipioId());
        }
        return d;
    }

    public PredioResponse toResponse(Predio d) {
        if (d == null) return null;
        PredioResponse r = new PredioResponse();
        r.setId(d.getId());
        r.setNombre(d.getNombre());
        r.setNumeroPredial(d.getNumeroPredial());
        r.setMatriculaInmobiliaria(d.getMatriculaInmobiliaria());
        r.setArea(d.getArea());
        r.setVereda(d.getVereda());
        r.setDescripcion(d.getDescripcion());
        r.setActivo(d.getActivo());
        r.setFechaCreacion(d.getFechaCreacion());
        r.setFechaActualizacion(d.getFechaActualizacion());
        if (d.getCoordenadas() != null) {
            r.setLatitud(d.getCoordenadas().getLatitud());
            r.setLongitud(d.getCoordenadas().getLongitud());
            r.setAltitud(d.getCoordenadas().getAltitud());
        }
        if (d.getLugarProduccion() != null) {
            r.setLugarProduccionId(d.getLugarProduccion().getId());
            r.setLugarProduccionNombre(d.getLugarProduccion().getNombre());
        }
        // Nuevo esquema: municipio viene de predio.id_municipio (FK directa)
        if (d.getIdMunicipio() != null) {
            r.setMunicipioId(d.getIdMunicipio());
        } else if (d.getLugarProduccion() != null && d.getLugarProduccion().getMunicipio() != null) {
            // Fallback: legacy path
            r.setMunicipioId(d.getLugarProduccion().getMunicipio().getId());
            r.setMunicipioNombre(d.getLugarProduccion().getMunicipio().getNombre());
        }
        return r;
    }

    public List<PredioResponse> toPredioResponseList(List<Predio> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Cultivo ───────────────────────────────────────────────────────────────

    public Cultivo toDomain(CultivoRequest req) {
        if (req == null) return null;
        Cultivo d = new Cultivo();
        d.setNombreVariedad(req.getNombreVariedad());
        d.setNombreCientifico(req.getNombreCientifico());
        d.setNombreComun(req.getNombreComun());
        d.setDescripcion(req.getDescripcion());
        // Diccionario: cultivo no tiene predio ni fechas como campos directos
        return d;
    }

    public CultivoResponse toResponse(Cultivo d) {
        if (d == null) return null;
        CultivoResponse r = new CultivoResponse();
        r.setId(d.getId());
        r.setNombreVariedad(d.getNombreVariedad());
        r.setNombreCientifico(d.getNombreCientifico());
        r.setNombreComun(d.getNombreComun());
        r.setDescripcion(d.getDescripcion());
        r.setFechaInicio(d.getFechaInicio());
        r.setFechaEstimadaCosecha(d.getFechaEstimadaCosecha());
        r.setActivo(d.getActivo());
        r.setEnTemporada(d.estaEnTemporada());
        r.setTotalLotes(d.totalLotes());
        r.setFechaCreacion(d.getFechaCreacion());
        r.setFechaActualizacion(d.getFechaActualizacion());
        if (d.getPredio() != null) {
            r.setPredioId(d.getPredio().getId());
            r.setPredioNombre(d.getPredio().getNombre());
        }
        if (d.getPlagas() != null) {
            r.setPlagas(d.getPlagas().stream().map(this::toResponse).collect(Collectors.toList()));
        }
        return r;
    }

    public List<CultivoResponse> toCultivoResponseList(List<Cultivo> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Lote ──────────────────────────────────────────────────────────────────

    public Lote toDomain(LoteRequest req) {
        if (req == null) return null;
        Lote d = new Lote();
        d.setNombre(req.getNombre());
        d.setArea(req.getArea());
        d.setEstado(req.getEstado());
        d.setFechaSiembra(req.getFechaSiembra());
        d.setFechaCosechaEstimada(req.getFechaCosechaEstimada());
        if (req.getCultivoId() != null) {
            Cultivo c = new Cultivo();
            c.setId(req.getCultivoId());
            d.setCultivo(c);
        }
        if (req.getIdLugar() != null) {
            LugarProduccion l = new LugarProduccion();
            l.setId(req.getIdLugar());
            d.setLugarProduccion(l);
        }
        return d;
    }

    public LoteResponse toResponse(Lote d) {
        if (d == null) return null;
        LoteResponse r = new LoteResponse();
        r.setId(d.getId());
        r.setNumero(d.getNumero());
        r.setNombre(d.getNombre());
        r.setArea(d.getArea());
        r.setEstado(d.getEstado());
        r.setEstadoEtiqueta(d.getEstado() != null ? d.getEstado().getEtiqueta() : null);
        r.setFechaSiembra(d.getFechaSiembra());
        r.setFechaCosechaEstimada(d.getFechaCosechaEstimada());
        r.setFechaCosechaReal(d.getFechaCosechaReal());
        r.setFechaCreacion(d.getFechaCreacion());
        r.setFechaActualizacion(d.getFechaActualizacion());
        if (d.getCoordenadas() != null) {
            r.setLatitud(d.getCoordenadas().getLatitud());
            r.setLongitud(d.getCoordenadas().getLongitud());
            r.setAltitud(d.getCoordenadas().getAltitud());
        }
        if (d.getCultivo() != null) {
            r.setCultivoId(d.getCultivo().getId());
            r.setCultivoNombre(d.getCultivo().getNombreComun());
        }
        if (d.getLugarProduccion() != null) {
            r.setIdLugar(d.getLugarProduccion().getId());
            r.setLugarNombre(d.getLugarProduccion().getNombre());
        } else if (d.getIdLugar() != null) {
            r.setIdLugar(d.getIdLugar());
        }
        if (d.getPlagas() != null) {
            r.setPlagas(d.getPlagas().stream().map(this::toResponse).collect(Collectors.toList()));
        }
        return r;
    }

    public List<LoteResponse> toLoteResponseList(List<Lote> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Plaga ─────────────────────────────────────────────────────────────────

    public Plaga toDomain(PlagaRequest req) {
        if (req == null) return null;
        Plaga d = new Plaga();
        d.setNombreComun(req.getNombreComun());
        d.setNombreCientifico(req.getNombreCientifico());
        d.setIdCultivo(req.getIdCultivo());
        return d;
    }

    public PlagaResponse toResponse(Plaga d) {
        if (d == null) return null;
        return PlagaResponse.builder()
                .id(d.getId())
                .nombreCientifico(d.getNombreCientifico())
                .nombreComun(d.getNombreComun())
                .descripcion(d.getDescripcion())
                .tipo(d.getTipo())
                .nivelRiesgo(d.getNivelRiesgo())
                .sintomas(d.getSintomas())
                .tratamiento(d.getTratamiento())
                .activo(d.getActivo())
                // Diccionario: plaga.id_cultivo
                .idCultivo(d.getIdCultivo())
                .fechaCreacion(d.getFechaCreacion())
                .fechaActualizacion(d.getFechaActualizacion())
                .build();
    }

    public List<PlagaResponse> toPlagaResponseList(List<Plaga> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
