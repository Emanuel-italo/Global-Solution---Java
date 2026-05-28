package br.com.orbitlink.space.entity;

import br.com.orbitlink.space.core.annotations.ChavePrimaria;
import br.com.orbitlink.space.core.annotations.ColunaMapeada;
import br.com.orbitlink.space.core.annotations.TabelaMapeada;
import br.com.orbitlink.space.enums.TipoAtivoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_ATIVO_ESPACIAL")
@TabelaMapeada(nome = "TBL_ATIVO_ESPACIAL")
public class AtivoEspacial {

    @Id
    @ChavePrimaria
    @SequenceGenerator(name = "seq_ativo_espacial", sequenceName = "SEQ_ATIVO_ESPACIAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ativo_espacial")
    @ColunaMapeada(nome = "ID")
    @Column(name = "ID")
    private Long id;

    @ColunaMapeada(nome = "NOME")
    @Column(name = "NOME", nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @ColunaMapeada(nome = "TIPO_ATIVO")
    @Column(name = "TIPO_ATIVO", nullable = false, length = 40)
    private TipoAtivoEnum tipoAtivo;

    @ColunaMapeada(nome = "AGENCIA_RESPONSAVEL")
    @Column(name = "AGENCIA_RESPONSAVEL", nullable = false, length = 120)
    private String agenciaResponsavel;

    @ColunaMapeada(nome = "DATA_LANCAMENTO")
    @Column(name = "DATA_LANCAMENTO", nullable = false)
    private LocalDate dataLancamento;

    @ColunaMapeada(nome = "MASSA_KG")
    @Column(name = "MASSA_KG", nullable = false, precision = 12, scale = 2)
    private BigDecimal massaKg;

    @ColunaMapeada(nome = "ESPECIFICACOES_TECNICAS")
    @Column(name = "ESPECIFICACOES_TECNICAS", nullable = false, length = 4000)
    private String especificacoesTecnicas;

    @ColunaMapeada(nome = "OPERACIONAL")
    @Column(name = "OPERACIONAL", nullable = false)
    private Boolean operacional = Boolean.TRUE;

    @OneToMany(mappedBy = "ativoEspacial", fetch = FetchType.LAZY)
    private List<ManutencaoOrbital> manutencoes = new ArrayList<>();

    @OneToMany(mappedBy = "ativoEspacial", fetch = FetchType.LAZY)
    private List<RegistroTelemetria> registrosTelemetria = new ArrayList<>();

    @OneToMany(mappedBy = "ativoEspacial", fetch = FetchType.LAZY)
    private List<AlertaOrbital> alertas = new ArrayList<>();

    public AtivoEspacial() {
    }

    public AtivoEspacial(Long id, String nome, TipoAtivoEnum tipoAtivo, String agenciaResponsavel,
                         LocalDate dataLancamento, BigDecimal massaKg, String especificacoesTecnicas,
                         Boolean operacional) {
        this.id = id;
        this.nome = nome;
        this.tipoAtivo = tipoAtivo;
        this.agenciaResponsavel = agenciaResponsavel;
        this.dataLancamento = dataLancamento;
        this.massaKg = massaKg;
        this.especificacoesTecnicas = especificacoesTecnicas;
        this.operacional = operacional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoAtivoEnum getTipoAtivo() {
        return tipoAtivo;
    }

    public void setTipoAtivo(TipoAtivoEnum tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }

    public String getAgenciaResponsavel() {
        return agenciaResponsavel;
    }

    public void setAgenciaResponsavel(String agenciaResponsavel) {
        this.agenciaResponsavel = agenciaResponsavel;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public BigDecimal getMassaKg() {
        return massaKg;
    }

    public void setMassaKg(BigDecimal massaKg) {
        this.massaKg = massaKg;
    }

    public String getEspecificacoesTecnicas() {
        return especificacoesTecnicas;
    }

    public void setEspecificacoesTecnicas(String especificacoesTecnicas) {
        this.especificacoesTecnicas = especificacoesTecnicas;
    }

    public Boolean getOperacional() {
        return operacional;
    }

    public void setOperacional(Boolean operacional) {
        this.operacional = operacional;
    }

    public List<ManutencaoOrbital> getManutencoes() {
        return manutencoes;
    }

    public void setManutencoes(List<ManutencaoOrbital> manutencoes) {
        this.manutencoes = manutencoes;
    }

    public List<RegistroTelemetria> getRegistrosTelemetria() {
        return registrosTelemetria;
    }

    public void setRegistrosTelemetria(List<RegistroTelemetria> registrosTelemetria) {
        this.registrosTelemetria = registrosTelemetria;
    }

    public List<AlertaOrbital> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<AlertaOrbital> alertas) {
        this.alertas = alertas;
    }
}
