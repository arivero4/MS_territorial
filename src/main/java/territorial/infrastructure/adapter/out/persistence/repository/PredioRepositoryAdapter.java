package territorial.infrastructure.adapter.out.persistence.repository;

import territorial.application.port.out.PredioRepositoryPort;
import territorial.domain.model.Predio;
import territorial.infrastructure.adapter.out.persistence.entity.PredioEntity;
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
public class PredioRepositoryAdapter implements PredioRepositoryPort {

    private final EntityManager em;
    private final TerritorialEntityMapper mapper;

    @Override
    public Predio guardar(Predio predio) {
        PredioEntity entity = mapper.toEntity(predio);
        if (entity.getId() == null) {
            em.persist(entity);
            return mapper.toDomain(entity);
        }
        return mapper.toDomain(em.merge(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Predio> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(PredioEntity.class, id)).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Predio> buscarPorNumeroPredial(String numeroPredial) {
        return em.createQuery(
                "SELECT p FROM PredioEntity p WHERE p.numeroPredial = :num", PredioEntity.class)
                .setParameter("num", numeroPredial)
                .getResultList().stream().findFirst().map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Predio> buscarTodos() {
        return em.createQuery("SELECT p FROM PredioEntity p ORDER BY p.nombre", PredioEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Predio> buscarPorLugarProduccionId(Long lugarProduccionId) {
        return em.createQuery(
                "SELECT p FROM PredioEntity p WHERE p.lugarProduccion.id = :lId ORDER BY p.nombre",
                PredioEntity.class)
                .setParameter("lId", lugarProduccionId)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Predio> buscarActivos() {
        return em.createQuery(
                "SELECT p FROM PredioEntity p WHERE p.activo = true ORDER BY p.nombre",
                PredioEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Predio> buscarPorNombreContiene(String nombre) {
        return em.createQuery(
                "SELECT p FROM PredioEntity p WHERE UPPER(p.nombre) LIKE UPPER(:nombre) ORDER BY p.nombre",
                PredioEntity.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        PredioEntity entity = em.find(PredioEntity.class, id);
        if (entity != null) em.remove(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        Long count = em.createQuery(
                "SELECT COUNT(p) FROM PredioEntity p WHERE p.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorNumeroPredial(String numeroPredial) {
        Long count = em.createQuery(
                "SELECT COUNT(p) FROM PredioEntity p WHERE p.numeroPredial = :num", Long.class)
                .setParameter("num", numeroPredial).getSingleResult();
        return count > 0;
    }
}
