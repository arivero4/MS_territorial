package territorial.infrastructure.adapter.out.persistence.jdbc;

import territorial.domain.enums.EstadoLote;
import territorial.domain.model.*;
import territorial.domain.valueobject.CodigoDane;
import territorial.domain.valueobject.Coordenadas;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
/** RowMapper JDBC auxiliar para consultas nativas sobre Oracle en ms-territorial. */

@Component
public class TerritorialRowMapper {

    public RowMapper<Departamento> departamentoRowMapper() {
        return (rs, rowNum) -> Departamento.builder()
                .id(rs.getLong("ID"))
                .nombre(rs.getString("NOMBRE"))
                .codigoDane(CodigoDane.deSinValidar(rs.getString("CODIGO_DANE")))
                .activo(rs.getBoolean("ACTIVO"))
                .fechaCreacion(toLocalDateTime(rs, "FECHA_CREACION"))
                .fechaActualizacion(toLocalDateTime(rs, "FECHA_ACTUALIZACION"))
                .build();
    }

    public RowMapper<Municipio> municipioRowMapper() {
        return (rs, rowNum) -> {
            Departamento dept = Departamento.builder()
                    .id(rs.getLong("DEPARTAMENTO_ID"))
                    .nombre(rs.getString("DEPARTAMENTO_NOMBRE"))
                    .build();
            return Municipio.builder()
                    .id(rs.getLong("ID"))
                    .nombre(rs.getString("NOMBRE"))
                    .codigoDane(CodigoDane.deSinValidar(rs.getString("CODIGO_DANE")))
                    .departamento(dept)
                    .activo(rs.getBoolean("ACTIVO"))
                    .fechaCreacion(toLocalDateTime(rs, "FECHA_CREACION"))
                    .build();
        };
    }

    public RowMapper<LugarProduccion> lugarRowMapper() {
        return (rs, rowNum) -> {
            Double lat = getDoubleNullable(rs, "LATITUD");
            Double lon = getDoubleNullable(rs, "LONGITUD");
            Double alt = getDoubleNullable(rs, "ALTITUD");
            Coordenadas coords = (lat != null && lon != null) ? Coordenadas.de(lat, lon, alt) : null;
            return LugarProduccion.builder()
                    .id(rs.getLong("ID"))
                    .nombre(rs.getString("NOMBRE"))
                    .descripcion(rs.getString("DESCRIPCION"))
                    .area(getDoubleNullable(rs, "AREA"))
                    .vereda(rs.getString("VEREDA"))
                    .coordenadas(coords)
                    .activo(rs.getBoolean("ACTIVO"))
                    .fechaCreacion(toLocalDateTime(rs, "FECHA_CREACION"))
                    .build();
        };
    }

    public RowMapper<Predio> predioRowMapper() {
        return (rs, rowNum) -> {
            Double lat = getDoubleNullable(rs, "LATITUD");
            Double lon = getDoubleNullable(rs, "LONGITUD");
            Coordenadas coords = (lat != null && lon != null)
                    ? Coordenadas.de(lat, lon, getDoubleNullable(rs, "ALTITUD")) : null;
            return Predio.builder()
                    .id(rs.getLong("ID"))
                    .nombre(rs.getString("NOMBRE"))
                    .numeroPredial(rs.getString("NUMERO_PREDIAL"))
                    .matriculaInmobiliaria(rs.getString("MATRICULA_INMOBILIARIA"))
                    .area(getDoubleNullable(rs, "AREA"))
                    .vereda(rs.getString("VEREDA"))
                    .descripcion(rs.getString("DESCRIPCION"))
                    .coordenadas(coords)
                    .activo(rs.getBoolean("ACTIVO"))
                    .fechaCreacion(toLocalDateTime(rs, "FECHA_CREACION"))
                    .build();
        };
    }

    public RowMapper<Lote> loteRowMapper() {
        return (rs, rowNum) -> {
            Double lat = getDoubleNullable(rs, "LATITUD");
            Double lon = getDoubleNullable(rs, "LONGITUD");
            Coordenadas coords = (lat != null && lon != null)
                    ? Coordenadas.de(lat, lon, getDoubleNullable(rs, "ALTITUD")) : null;
            String estadoStr = rs.getString("ESTADO");
            return Lote.builder()
                    .id(rs.getLong("ID"))
                    .numero(rs.getString("NUMERO"))
                    .nombre(rs.getString("NOMBRE"))
                    .area(getDoubleNullable(rs, "AREA"))
                    .estado(estadoStr != null ? EstadoLote.valueOf(estadoStr) : null)
                    .fechaSiembra(toLocalDate(rs, "FECHA_SIEMBRA"))
                    .fechaCosechaEstimada(toLocalDate(rs, "FECHA_COSECHA_ESTIMADA"))
                    .fechaCosechaReal(toLocalDate(rs, "FECHA_COSECHA_REAL"))
                    .coordenadas(coords)
                    .fechaCreacion(toLocalDateTime(rs, "FECHA_CREACION"))
                    .build();
        };
    }

    public RowMapper<Plaga> plagaRowMapper() {
        return (rs, rowNum) -> Plaga.builder()
                .id(rs.getLong("ID"))
                .nombreCientifico(rs.getString("NOMBRE_CIENTIFICO"))
                .nombreComun(rs.getString("NOMBRE_COMUN"))
                .descripcion(rs.getString("DESCRIPCION"))
                .tipo(rs.getString("TIPO"))
                .nivelRiesgo(rs.getString("NIVEL_RIESGO"))
                .sintomas(rs.getString("SINTOMAS"))
                .tratamiento(rs.getString("TRATAMIENTO"))
                .activo(rs.getBoolean("ACTIVO"))
                .fechaCreacion(toLocalDateTime(rs, "FECHA_CREACION"))
                .build();
    }

    private LocalDateTime toLocalDateTime(ResultSet rs, String col) throws SQLException {
        java.sql.Timestamp ts = rs.getTimestamp(col);
        return ts != null ? ts.toLocalDateTime() : null;
    }

    private LocalDate toLocalDate(ResultSet rs, String col) throws SQLException {
        java.sql.Date d = rs.getDate(col);
        return d != null ? d.toLocalDate() : null;
    }

    private Double getDoubleNullable(ResultSet rs, String col) throws SQLException {
        double val = rs.getDouble(col);
        return rs.wasNull() ? null : val;
    }
}
