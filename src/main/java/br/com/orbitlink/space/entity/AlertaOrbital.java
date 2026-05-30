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

}