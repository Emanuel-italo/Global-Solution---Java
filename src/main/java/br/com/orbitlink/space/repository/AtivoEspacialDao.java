package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AtivoEspacialDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AtivoEspacial salvar(AtivoEspacial ativo) {
        if (ativo.getId() == null) {
            entityManager.persist(ativo);
            return ativo;
        }
        return entityManager.merge(ativo);
    }

    public Optional<AtivoEspacial> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.find(AtivoEspacial.class, id));
    }

    public AtivoEspacial buscarPorIdOuFalhar(Long id) {
        return buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoLocalizadaException("Ativo espacial não encontrado com id " + id));
    }

    @SuppressWarnings("unchecked")
    public List<AtivoEspacial> listarTodos() {
        return entityManager.createQuery("select a from AtivoEspacial a order by a.id", AtivoEspacial.class)
                .getResultList();
    }

    public AtivoEspacial atualizar(AtivoEspacial ativo) {
        return entityManager.merge(ativo);
    }

    public void descomissionar(Long id) {
        int atualizados = entityManager.createQuery(
                        "update AtivoEspacial a set a.operacional = false where a.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        if (atualizados == 0) {
            throw new EntidadeNaoLocalizadaException("Ativo espacial não encontrado com id " + id);
        }
    }

    public boolean existePorId(Long id) {
        return buscarPorId(id).isPresent();
    }
}
