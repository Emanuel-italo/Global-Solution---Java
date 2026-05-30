package br.com.orbitlink.space.entity;

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
public class ManutencaoOrbital {
    @Id
    @SequenceGenerator(name = "seq_manutencao_orbital", sequenceName = "SEQ_MANUTENCAO_ORBITAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manutencao_orbital")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATIVO_ID", nullable = false)
    private AtivoEspacial ativoEspacial;

    @Column(name = "DATA_MANUTENCAO", nullable = false)
    private LocalDateTime dataManutencao;

    @Column(name = "DESCRICAO", nullable = false, length = 2000)
    private String descricao;

    

}