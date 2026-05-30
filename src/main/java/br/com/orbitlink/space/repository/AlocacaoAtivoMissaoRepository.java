package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.AlocacaoAtivoMissao;
import br.com.orbitlink.space.entity.AlocacaoAtivoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlocacaoAtivoMissaoRepository extends JpaRepository<AlocacaoAtivoMissao, AlocacaoAtivoMissaoId> {
}