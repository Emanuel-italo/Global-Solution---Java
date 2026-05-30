package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.AlertaOrbital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaOrbitalRepository extends JpaRepository<AlertaOrbital, Long> {

    @Query("select a from AlertaOrbital a join fetch a.ativoEspacial where a.id = :id")
    Optional<AlertaOrbital> buscarComAtivoPorId(@Param("id") Long id);

    @Query("select a from AlertaOrbital a join fetch a.ativoEspacial order by a.id")
    List<AlertaOrbital> buscarTodosComAtivo();

    @Query("select a from AlertaOrbital a join fetch a.ativoEspacial where a.ativoEspacial.id = :ativoId order by a.id")
    List<AlertaOrbital> buscarPorAtivoId(@Param("ativoId") Long ativoId);
}