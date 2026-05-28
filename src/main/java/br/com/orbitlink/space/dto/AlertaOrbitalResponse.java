package br.com.orbitlink.space.dto;

import br.com.orbitlink.space.enums.CriticidadeAlertaEnum;
import br.com.orbitlink.space.enums.TipoAlertaEnum;
import java.time.LocalDateTime;

public record AlertaOrbitalResponse(
        Long id,
        Long ativoId,
        String ativoNome,
        TipoAlertaEnum tipoAlerta,
        CriticidadeAlertaEnum criticidade,
        String mensagem,
        LocalDateTime dataGeracao,
        Boolean resolvido
) {}
