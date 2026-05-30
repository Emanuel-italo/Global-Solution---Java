package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.ManutencaoOrbital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManutencaoOrbitalRepository extends JpaRepository<ManutencaoOrbital, Long> {

    @Query("select m from ManutencaoOrbital m join fetch m.ativoEspacial where m.id = :id")
    Optional<ManutencaoOrbital> buscarComAtivoPorId(@Param("id") Long id);

    @Query("select m from ManutencaoOrbital m join fetch m.ativoEspacial order by m.id")
    List<ManutencaoOrbital> buscarTodosComAtivo();

    @Query("select m from ManutencaoOrbital m join fetch m.ativoEspacial where m.ativoEspacial.id = :ativoId order by m.id")
    List<ManutencaoOrbital> buscarPorAtivoId(@Param("ativoId") Long ativoId);
}