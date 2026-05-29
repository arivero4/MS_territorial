package territorial.infrastructure.adapter.out.persistence.repository;

import territorial.application.port.out.LoteRepositoryPort;
import territorial.domain.enums.EstadoLote;
import territorial.domain.model.Lote;
import territorial.infrastructure.adapter.out.persistence.entity.LoteEntity;
import territorial.infrastructure.adapter.out.persistence.entity.PlagaEntity;
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
public class LoteRepositoryAdapter implements LoteRepositoryPort {

    private final EntityManager em;
    private final TerritorialEntityMapper mapper;

    @Override
    public Lote guardar(Lote lote) {
        LoteEntity entity = mapper.toEntity(lote);
        if (entity.getId() == null) {
            em.persist(entity);
            return mapper.toDomain(entity);
        }
        return mapper.toDomain(em.merge(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Lote> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(LoteEntity.class, id)).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lote> buscarTodos() {
        return em.createQuery("SELECT l FROM LoteEntity l ORDER BY l.numero", LoteEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lote> buscarPorCultivoId(Long cultivoId) {
        return em.createQuery(
                "SELECT l FROM LoteEntity l WHERE l.cultivo.id = :cId ORDER BY l.numero",
                LoteEntity.class)
                .setParameter("cId", cultivoId)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lote> buscarPorEstado(EstadoLote estado) {
        return em.createQuery(
                "SELECT l FROM LoteEntity l WHERE l.estado = :estado ORDER BY l.numero",
                LoteEntity.class)
                .setParameter("estado", estado.name())
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lote> buscarConAltaPlaga() {
        return em.createQuery(
                "SELECT DISTINCT l FROM LoteEntity l JOIN l.plagas p WHERE p.nivelRiesgo = 'ALTO'",
                LoteEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        LoteEntity entity = em.find(LoteEntity.class, id);
        if (entity != null) em.remove(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        Long count = em.createQuery(
                "SELECT COUNT(l) FROM LoteEntity l WHERE l.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return count > 0;
    }

    @Override
    public void asociarPlaga(Long loteId, Long plagaId) {
        LoteEntity lote = em.find(LoteEntity.class, loteId);
        PlagaEntity plaga = em.find(PlagaEntity.class, plagaId);
        if (lote != null && plaga != null && !lote.getPlagas().contains(plaga)) {
            lote.getPlagas().add(plaga);
        }
    }

    @Override
    public void desasociarPlaga(Long loteId, Long plagaId) {
        LoteEntity lote = em.find(LoteEntity.class, loteId);
        PlagaEntity plaga = em.find(PlagaEntity.class, plagaId);
        if (lote != null && plaga != null) {
            lote.getPlagas().remove(plaga);
        }
    }
}
