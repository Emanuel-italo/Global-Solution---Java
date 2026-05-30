package br.com.orbitlink.space.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "TB_ALOCACAO_ATIVO_MISSAO")
@Data
public class AlocacaoAtivoMissao {

    @EmbeddedId
    private AlocacaoAtivoMissaoId id = new AlocacaoAtivoMissaoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ativoId")
    @JoinColumn(name = "ativo_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AtivoEspacial ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("missaoId")
    @JoinColumn(name = "missao_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MissaoEspacial missao;

    @Column(name = "dt_alocacao", nullable = false)
    private LocalDate dataAlocacao;

    @Column(name = "ds_papel", length = 100)
    private String papel;
}