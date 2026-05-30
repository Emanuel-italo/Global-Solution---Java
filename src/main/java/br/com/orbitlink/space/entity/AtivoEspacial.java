package br.com.orbitlink.space.entity;

import br.com.orbitlink.space.enums.TipoAtivoEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TB_ATIVO_ESPACIAL")
@EqualsAndHashCode(callSuper = true)
@Data
public class AtivoEspacial extends EquipamentoOrbital {

    @Column(name = "nm_ativo", nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_tipo", nullable = false)
    private TipoAtivoEnum tipo;

    @Embedded // Trazendo os atributos da classe CoordenadasEspaciais para esta tabela
    private CoordenadasEspaciais coordenadasAtuais;

    // CORREÇÃO: Adicionado o campo 'operacional' para o método de descomissionar funcionar perfeitamente
    @Column(name = "is_operacional", nullable = false)
    private Boolean operacional = true;
}