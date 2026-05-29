package territorial.infrastructure.adapter.out.persistence.repository;

import territorial.application.port.out.LugarRepositoryPort;
import territorial.domain.model.LugarProduccion;
import territorial.infrastructure.adapter.out.persistence.entity.LugarEntity;
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
public class LugarRepositoryAdapter implements LugarRepositoryPort {

    private final EntityManager em;
    private final TerritorialEntityMapper mapper;

    @Override
    public LugarProduccion guardar(LugarProduccion lugar) {
        LugarEntity entity = mapper.toEntity(lugar);
        if (entity.getId() == null) {
            em.persist(entity);
            return mapper.toDomain(entity);
        }
        return mapper.toDomain(em.merge(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LugarProduccion> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(LugarEntity.class, id)).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarProduccion> buscarTodos() {
        return em.createQuery("SELECT l FROM LugarEntity l ORDER BY l.nombre", LugarEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarProduccion> buscarPorMunicipioId(Long municipioId) {
        return em.createQuery(
                "SELECT l FROM LugarEntity l WHERE l.municipio.id = :mId ORDER BY l.nombre",
                LugarEntity.class)
                .setParameter("mId", municipioId)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarProduccion> buscarActivos() {
        return em.createQuery(
                "SELECT l FROM LugarEntity l WHERE l.activo = true ORDER BY l.nombre",
                LugarEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        LugarEntity entity = em.find(LugarEntity.class, id);
        if (entity != null) em.remove(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        Long count = em.createQuery(
                "SELECT COUNT(l) FROM LugarEntity l WHERE l.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return count > 0;
    }
}
