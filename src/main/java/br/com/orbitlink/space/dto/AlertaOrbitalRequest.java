package br.com.orbitlink.space.dto;

import br.com.orbitlink.space.enums.CriticidadeAlertaEnum;
import br.com.orbitlink.space.enums.TipoAlertaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AlertaOrbitalRequest(
        @NotNull(message = "O ID do ativo espacial é obrigatório.")
        Long ativoId,

        @NotNull(message = "O tipo de alerta é obrigatório.")
        TipoAlertaEnum tipoAlerta,

        @NotNull(message = "A criticidade é obrigatória.")
        CriticidadeAlertaEnum criticidade,

        @NotBlank(message = "A mensagem do alerta é obrigatória.")
        String mensagem,

        @NotNull(message = "A data de geração é obrigatória.")
        LocalDateTime dataGeracao,

        @NotNull(message = "O campo resolvido é obrigatório.")
        Boolean resolvido
) {}
