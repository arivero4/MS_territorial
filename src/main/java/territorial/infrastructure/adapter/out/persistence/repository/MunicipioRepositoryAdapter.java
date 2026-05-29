package territorial.infrastructure.adapter.out.persistence.repository;

import territorial.application.port.out.MunicipioRepositoryPort;
import territorial.domain.model.Municipio;
import territorial.domain.valueobject.CodigoDane;
import territorial.infrastructure.adapter.out.persistence.entity.MunicipioEntity;
import territorial.infrastructure.adapter.out.persistence.mapper.TerritorialEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional
public class MunicipioRepositoryAdapter implements MunicipioRepositoryPort {

    private final EntityManager em;
    private final TerritorialEntityMapper mapper;

    @Override
    public Municipio guardar(Municipio municipio) {
        MunicipioEntity entity = mapper.toEntity(municipio);
        if (entity.getId() == null) {
            em.persist(entity);
            return mapper.toDomain(entity);
        }
        return mapper.toDomain(em.merge(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Municipio> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(MunicipioEntity.class, id)).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Municipio> buscarPorCodigoDane(CodigoDane codigoDane) {
        return em.createQuery(
                "SELECT m FROM MunicipioEntity m WHERE m.codigoDane = :codigo", MunicipioEntity.class)
                .setParameter("codigo", codigoDane.getCodigo())
                .getResultList().stream().findFirst().map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Municipio> buscarTodos() {
        return em.createQuery("SELECT m FROM MunicipioEntity m ORDER BY m.nombre", MunicipioEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Municipio> buscarPorDepartamentoId(Long departamentoId) {
        return em.createQuery(
                "SELECT m FROM MunicipioEntity m WHERE m.departamento.id = :deptId ORDER BY m.nombre",
                MunicipioEntity.class)
                .setParameter("deptId", departamentoId)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Municipio> buscarActivos() {
        return em.createQuery(
                "SELECT m FROM MunicipioEntity m WHERE m.activo = true ORDER BY m.nombre",
                MunicipioEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        MunicipioEntity entity = em.find(MunicipioEntity.class, id);
        if (entity != null) em.remove(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        Long count = em.createQuery(
                "SELECT COUNT(m) FROM MunicipioEntity m WHERE m.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoDane(CodigoDane codigoDane) {
        Long count = em.createQuery(
                "SELECT COUNT(m) FROM MunicipioEntity m WHERE m.codigoDane = :codigo", Long.class)
                .setParameter("codigo", codigoDane.getCodigo()).getSingleResult();
        return count > 0;
    }
}
