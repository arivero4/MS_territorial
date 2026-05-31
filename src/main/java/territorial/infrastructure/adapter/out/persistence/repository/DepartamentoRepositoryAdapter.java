package territorial.infrastructure.adapter.out.persistence.repository;

import territorial.application.port.out.DepartamentoRepositoryPort;
import territorial.domain.model.Departamento;
import territorial.domain.valueobject.CodigoDane;
import territorial.infrastructure.adapter.out.persistence.entity.DepartamentoEntity;
import territorial.infrastructure.adapter.out.persistence.mapper.TerritorialEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/** Adaptador JPA que implementa DepartamentoRepositoryPort sobre Oracle 10g. */

@Repository
@RequiredArgsConstructor
@Transactional
public class DepartamentoRepositoryAdapter implements DepartamentoRepositoryPort {

    private final EntityManager em;
    private final TerritorialEntityMapper mapper;

    @Override
    public Departamento guardar(Departamento departamento) {
        DepartamentoEntity entity = mapper.toEntity(departamento);
        if (entity.getId() == null) {
            em.persist(entity);
            return mapper.toDomain(entity);
        }
        return mapper.toDomain(em.merge(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Departamento> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(DepartamentoEntity.class, id)).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Departamento> buscarPorCodigoDane(CodigoDane codigoDane) {
        TypedQuery<DepartamentoEntity> q = em.createQuery(
                "SELECT d FROM DepartamentoEntity d WHERE d.codigoDane = :codigo",
                DepartamentoEntity.class);
        q.setParameter("codigo", codigoDane.getCodigo());
        return q.getResultList().stream().findFirst().map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Departamento> buscarTodos() {
        return em.createQuery("SELECT d FROM DepartamentoEntity d ORDER BY d.nombre", DepartamentoEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Departamento> buscarActivos() {
        // DEPARTAMENTO no longer has activo column in new schema — return all
        return buscarTodos();
    }

    @Override
    public void eliminar(Long id) {
        DepartamentoEntity entity = em.find(DepartamentoEntity.class, id);
        if (entity != null) em.remove(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        Long count = em.createQuery(
                "SELECT COUNT(d) FROM DepartamentoEntity d WHERE d.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoDane(CodigoDane codigoDane) {
        Long count = em.createQuery(
                "SELECT COUNT(d) FROM DepartamentoEntity d WHERE d.codigoDane = :codigo", Long.class)
                .setParameter("codigo", codigoDane.getCodigo()).getSingleResult();
        return count > 0;
    }
}
