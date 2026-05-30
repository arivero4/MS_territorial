package territorial.infrastructure.adapter.out.persistence.repository;

import territorial.application.port.out.CultivoRepositoryPort;
import territorial.domain.model.Cultivo;
import territorial.infrastructure.adapter.out.persistence.entity.CultivoEntity;
import territorial.infrastructure.adapter.out.persistence.entity.PlagaEntity;
import territorial.infrastructure.adapter.out.persistence.mapper.TerritorialEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional
public class CultivoRepositoryAdapter implements CultivoRepositoryPort {

    private final EntityManager em;
    private final TerritorialEntityMapper mapper;

    @Override
    public Cultivo guardar(Cultivo cultivo) {
        CultivoEntity entity = mapper.toEntity(cultivo);
        if (entity.getId() == null) {
            em.persist(entity);
            return mapper.toDomain(entity);
        }
        return mapper.toDomain(em.merge(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cultivo> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(CultivoEntity.class, id)).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> buscarTodos() {
        return em.createQuery("SELECT c FROM CultivoEntity c ORDER BY c.nombreComun", CultivoEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> buscarPorPredioId(Long predioId) {
        // CULTIVO no longer has predio FK in new schema — return all
        return buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> buscarEnTemporada() {
        // CULTIVO no longer has fecha columns in new schema — return all
        return buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> buscarActivos() {
        // CULTIVO no longer has activo column in new schema — return all
        return buscarTodos();
    }

    @Override
    public void eliminar(Long id) {
        CultivoEntity entity = em.find(CultivoEntity.class, id);
        if (entity != null) em.remove(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        Long count = em.createQuery(
                "SELECT COUNT(c) FROM CultivoEntity c WHERE c.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return count > 0;
    }

    @Override
    public void asociarPlaga(Long cultivoId, Long plagaId) {
        CultivoEntity cultivo = em.find(CultivoEntity.class, cultivoId);
        PlagaEntity plaga = em.find(PlagaEntity.class, plagaId);
        if (cultivo != null && plaga != null) {
            plaga.setCultivo(cultivo);
            em.merge(plaga);
        }
    }

    @Override
    public void desasociarPlaga(Long cultivoId, Long plagaId) {
        PlagaEntity plaga = em.find(PlagaEntity.class, plagaId);
        if (plaga != null) {
            plaga.setCultivo(null);
            em.merge(plaga);
        }
    }
}
