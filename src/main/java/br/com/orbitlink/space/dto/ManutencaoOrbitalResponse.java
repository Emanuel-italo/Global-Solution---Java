package br.com.orbitlink.space.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ManutencaoOrbitalResponse(
        Long id,
        Long ativoId,
        String ativoNome,
        LocalDateTime dataManutencao,
        String descricao,
        BigDecimal custoEstimado
) {}
