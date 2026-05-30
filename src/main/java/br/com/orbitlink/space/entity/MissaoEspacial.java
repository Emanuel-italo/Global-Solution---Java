package br.com.orbitlink.space.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TB_MISSAO_ESPACIAL")
@Data
public class MissaoEspacial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_missao", nullable = false, length = 120)
    private String nome;

    @Column(name = "ds_objetivo", length = 500)
    private String objetivo;
}