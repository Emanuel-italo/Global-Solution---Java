package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.RegistroTelemetria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroTelemetriaRepository extends JpaRepository<RegistroTelemetria, Long> {

    @Query("select r from RegistroTelemetria r join fetch r.ativoEspacial where r.id = :id")
    Optional<RegistroTelemetria> buscarComAtivoPorId(@Param("id") Long id);

    @Query("select r from RegistroTelemetria r join fetch r.ativoEspacial order by r.id")
    List<RegistroTelemetria> buscarTodosComAtivo();

    @Query("select r from RegistroTelemetria r join fetch r.ativoEspacial where r.ativoEspacial.id = :ativoId order by r.id")
    List<RegistroTelemetria> buscarPorAtivoId(@Param("ativoId") Long ativoId);
}