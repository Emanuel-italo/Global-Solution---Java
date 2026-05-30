package br.com.orbitlink.space.repository;

import br.com.orbitlink.space.entity.AtivoEspacial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AtivoEspacialRepository extends JpaRepository<AtivoEspacial, Long> {

    // Substitui o método "descomissionar" que você tinha no DAO antigo
    @Modifying
    @Query("UPDATE AtivoEspacial a SET a.operacional = false WHERE a.id = :id")
    int descomissionarAtivo(Long id);
}