package br.com.orbitlink.space.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlocacaoAtivoMissaoId implements Serializable {
    private Long ativoId;
    private Long missaoId;
}