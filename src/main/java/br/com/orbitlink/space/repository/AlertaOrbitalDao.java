package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.AlertaOrbital;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AlertaOrbitalDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AlertaOrbital salvar(AlertaOrbital alerta) {
        if (alerta.getId() == null) {
            entityManager.persist(alerta);
            return alerta;
        }
        return entityManager.merge(alerta);
    }

    public Optional<AlertaOrbital> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.createQuery(
                        "select a from AlertaOrbital a join fetch a.ativoEspacial where a.id = :id",
                        AlertaOrbital.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null));
    }

    public AlertaOrbital buscarPorIdOuFalhar(Long id) {
        return buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoLocalizadaException("Alerta orbital não encontrado com id " + id));
    }

    public List<AlertaOrbital> listarTodos() {
        return entityManager.createQuery(
                        "select a from AlertaOrbital a join fetch a.ativoEspacial order by a.id",
                        AlertaOrbital.class)
                .getResultList();
    }

    public List<AlertaOrbital> listarPorAtivoId(Long ativoId) {
        return entityManager.createQuery(
                        "select a from AlertaOrbital a join fetch a.ativoEspacial where a.ativoEspacial.id = :ativoId order by a.id",
                        AlertaOrbital.class)
                .setParameter("ativoId", ativoId)
                .getResultList();
    }

    public void deletar(Long id) {
        AlertaOrbital alerta = buscarPorIdOuFalhar(id);
        entityManager.remove(alerta);
    }
}
