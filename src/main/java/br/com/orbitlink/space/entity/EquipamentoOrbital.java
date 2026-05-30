package br.com.orbitlink.space.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TB_EQUIPAMENTO_ORBITAL")
@Inheritance(strategy = InheritanceType.JOINED) // Estratégia de herança exigida
@Data
public abstract class EquipamentoOrbital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeProprietario;

    @Column(nullable = false)
    private String numeroDeSerie;
}