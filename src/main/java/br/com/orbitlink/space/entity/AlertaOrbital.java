package br.com.orbitlink.space.entity;

import br.com.orbitlink.space.enums.CriticidadeAlertaEnum;
import br.com.orbitlink.space.enums.TipoAlertaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_ALERTA_ORBITAL")
public class AlertaOrbital {

    @Id
    @SequenceGenerator(name = "seq_alerta_orbital", sequenceName = "SEQ_ALERTA_ORBITAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alerta_orbital")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATIVO_ID", nullable = false)
    private AtivoEspacial ativoEspacial;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ALERTA", nullable = false, length = 60)
    private TipoAlertaEnum tipoAlerta;

    @Enumerated(EnumType.STRING)
    @Column(name = "CRITICIDADE", nullable = false, length = 20)
    private CriticidadeAlertaEnum criticidade;

    @Column(name = "MENSAGEM", nullable = false, length = 2000)
    private String mensagem;


    @Column(name = "DATA_GERACAO", nullable = false)
    private LocalDateTime dataGeracao;

    @Column(name = "RESOLVIDO", nullable = false)
    private Boolean resolvido = Boolean.FALSE;

    public AlertaOrbital() {
    }

    public AlertaOrbital(Long id, AtivoEspacial ativoEspacial, TipoAlertaEnum tipoAlerta, CriticidadeAlertaEnum criticidade, String mensagem, LocalDateTime dataGeracao, Boolean resolvido) {
        this.id = id;
        this.ativoEspacial = ativoEspacial;
        this.tipoAlerta = tipoAlerta;
        this.criticidade = criticidade;
        this.mensagem = mensagem;
        this.dataGeracao = dataGeracao;
        this.resolvido = resolvido;
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

    public TipoAlertaEnum getTipoAlerta() {
        return tipoAlerta;
    }


    

}