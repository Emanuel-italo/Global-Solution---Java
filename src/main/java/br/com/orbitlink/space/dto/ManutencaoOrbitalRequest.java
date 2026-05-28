package br.com.orbitlink.space.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ManutencaoOrbitalRequest(
        @NotNull(message = "O ID do ativo espacial é obrigatório.")
        Long ativoId,

        @NotNull(message = "A data da manutenção é obrigatória.")
        LocalDateTime dataManutencao,

        @NotBlank(message = "A descrição da manutenção é obrigatória.")
        String descricao,

        @PositiveOrZero(message = "O custo estimado não pode ser negativo.")
        BigDecimal custoEstimado
) {}
