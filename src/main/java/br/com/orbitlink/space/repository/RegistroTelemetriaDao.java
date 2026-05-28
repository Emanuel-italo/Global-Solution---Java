package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.RegistroTelemetria;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RegistroTelemetriaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public RegistroTelemetria salvar(RegistroTelemetria registro) {
        if (registro.getId() == null) {
            entityManager.persist(registro);
            return registro;
        }
        return entityManager.merge(registro);
    }

    public Optional<RegistroTelemetria> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.createQuery(
                        "select r from RegistroTelemetria r join fetch r.ativoEspacial where r.id = :id",
                        RegistroTelemetria.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null));
    }

    public RegistroTelemetria buscarPorIdOuFalhar(Long id) {
        return buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoLocalizadaException("Registro de telemetria não encontrado com id " + id));
    }

    public List<RegistroTelemetria> listarTodos() {
        return entityManager.createQuery(
                        "select r from RegistroTelemetria r join fetch r.ativoEspacial order by r.id",
                        RegistroTelemetria.class)
                .getResultList();
    }

    public List<RegistroTelemetria> listarPorAtivoId(Long ativoId) {
        return entityManager.createQuery(
                        "select r from RegistroTelemetria r join fetch r.ativoEspacial where r.ativoEspacial.id = :ativoId order by r.id",
                        RegistroTelemetria.class)
                .setParameter("ativoId", ativoId)
                .getResultList();
    }

    public void deletar(Long id) {
        RegistroTelemetria registro = buscarPorIdOuFalhar(id);
        entityManager.remove(registro);
    }
}
