package br.com.orbitlink.space.entity;

import br.com.orbitlink.space.core.annotations.ChavePrimaria;
import br.com.orbitlink.space.core.annotations.ColunaMapeada;
import br.com.orbitlink.space.core.annotations.TabelaMapeada;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_MANUTENCAO_ORBITAL")
@TabelaMapeada(nome = "TBL_MANUTENCAO_ORBITAL")
public class ManutencaoOrbital {

    @Id
    @ChavePrimaria
    @SequenceGenerator(name = "seq_manutencao_orbital", sequenceName = "SEQ_MANUTENCAO_ORBITAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manutencao_orbital")
    @ColunaMapeada(nome = "ID")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATIVO_ID", nullable = false)
    private AtivoEspacial ativoEspacial;

    @ColunaMapeada(nome = "DATA_MANUTENCAO")
    @Column(name = "DATA_MANUTENCAO", nullable = false)
    private LocalDateTime dataManutencao;

    @ColunaMapeada(nome = "DESCRICAO")
    @Column(name = "DESCRICAO", nullable = false, length = 2000)
    private String descricao;

    @ColunaMapeada(nome = "CUSTO_ESTIMADO")
    @Column(name = "CUSTO_ESTIMADO", precision = 14, scale = 2)
    private BigDecimal custoEstimado;

    public ManutencaoOrbital() {
    }

    public ManutencaoOrbital(Long id, AtivoEspacial ativoEspacial, LocalDateTime dataManutencao, String descricao, BigDecimal custoEstimado) {
        this.id = id;
        this.ativoEspacial = ativoEspacial;
        this.dataManutencao = dataManutencao;
        this.descricao = descricao;
        this.custoEstimado = custoEstimado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtivoEspacial getAtivoEspacial() {
        return ativoEspacial;
    }

    public void setAtivoEspacial(AtivoEspacial ativoEspacial) {
        this.ativoEspacial = ativoEspacial;
    }

    public LocalDateTime getDataManutencao() {
        return dataManutencao;
    }

    public void setDataManutencao(LocalDateTime dataManutencao) {
        this.dataManutencao = dataManutencao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getCustoEstimado() {
        return custoEstimado;
    }

    public void setCustoEstimado(BigDecimal custoEstimado) {
        this.custoEstimado = custoEstimado;
    }
}
