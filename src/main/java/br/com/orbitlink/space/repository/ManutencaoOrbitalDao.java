package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.ManutencaoOrbital;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ManutencaoOrbitalDao {

    @PersistenceContext
    private EntityManager entityManager;

    public ManutencaoOrbital salvar(ManutencaoOrbital manutencao) {
        if (manutencao.getId() == null) {
            entityManager.persist(manutencao);
            return manutencao;
        }
        return entityManager.merge(manutencao);
    }

    public Optional<ManutencaoOrbital> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.createQuery(
                        "select m from ManutencaoOrbital m join fetch m.ativoEspacial where m.id = :id",
                        ManutencaoOrbital.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null));
    }

    public ManutencaoOrbital buscarPorIdOuFalhar(Long id) {
        return buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoLocalizadaException("Manutenção orbital não encontrada com id " + id));
    }

    public List<ManutencaoOrbital> listarTodos() {
        return entityManager.createQuery(
                        "select m from ManutencaoOrbital m join fetch m.ativoEspacial order by m.id",
                        ManutencaoOrbital.class)
                .getResultList();
    }

    public List<ManutencaoOrbital> listarPorAtivoId(Long ativoId) {
        return entityManager.createQuery(
                        "select m from ManutencaoOrbital m join fetch m.ativoEspacial where m.ativoEspacial.id = :ativoId order by m.id",
                        ManutencaoOrbital.class)
                .setParameter("ativoId", ativoId)
                .getResultList();
    }

    public void deletar(Long id) {
        ManutencaoOrbital manutencao = buscarPorIdOuFalhar(id);
        entityManager.remove(manutencao);
    }
}
