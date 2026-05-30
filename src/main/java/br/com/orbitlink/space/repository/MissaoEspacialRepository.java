package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.MissaoEspacial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissaoEspacialRepository extends JpaRepository<MissaoEspacial, Long> {
}