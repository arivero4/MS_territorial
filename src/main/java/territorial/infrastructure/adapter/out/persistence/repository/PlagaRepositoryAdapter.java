package territorial.infrastructure.adapter.out.persistence.repository;

import territorial.application.port.out.PlagaRepositoryPort;
import territorial.domain.model.Plaga;
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
public class PlagaRepositoryAdapter implements PlagaRepositoryPort {

    private final EntityManager em;
    private final TerritorialEntityMapper mapper;

    @Override
    public Plaga guardar(Plaga plaga) {
        PlagaEntity entity = mapper.toEntity(plaga);
        if (entity.getId() == null) {
            em.persist(entity);
            return mapper.toDomain(entity);
        }
        return mapper.toDomain(em.merge(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plaga> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(PlagaEntity.class, id)).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> buscarTodas() {
        return em.createQuery("SELECT p FROM PlagaEntity p ORDER BY p.nombreComun", PlagaEntity.class)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> buscarPorTipo(String tipo) {
        return em.createQuery(
                "SELECT p FROM PlagaEntity p WHERE UPPER(p.tipo) = UPPER(:tipo) ORDER BY p.nombreComun",
                PlagaEntity.class)
                .setParameter("tipo", tipo)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> buscarPorNivelRiesgo(String nivelRiesgo) {
        return em.createQuery(
                "SELECT p FROM PlagaEntity p WHERE UPPER(p.nivelRiesgo) = UPPER(:nivel) ORDER BY p.nombreComun",
                PlagaEntity.class)
                .setParameter("nivel", nivelRiesgo)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> buscarPorNombreContiene(String nombre) {
        return em.createQuery(
                "SELECT p FROM PlagaEntity p WHERE UPPER(p.nombreComun) LIKE UPPER(:nombre) OR UPPER(p.nombreCientifico) LIKE UPPER(:nombre)",
                PlagaEntity.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> buscarPorLoteId(Long loteId) {
        return em.createQuery(
                "SELECT p FROM PlagaEntity p JOIN p IN (SELECT l.plagas FROM LoteEntity l WHERE l.id = :lId)",
                PlagaEntity.class)
                .setParameter("lId", loteId)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaga> buscarPorCultivoId(Long cultivoId) {
        return em.createQuery(
                "SELECT p FROM CultivoEntity c JOIN c.plagas p WHERE c.id = :cId",
                PlagaEntity.class)
                .setParameter("cId", cultivoId)
                .getResultList().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        PlagaEntity entity = em.find(PlagaEntity.class, id);
        if (entity != null) em.remove(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        Long count = em.createQuery(
                "SELECT COUNT(p) FROM PlagaEntity p WHERE p.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return count > 0;
    }
}
