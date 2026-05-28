package br.com.orbitlink.space.dto;

import br.com.orbitlink.space.enums.TipoAtivoEnum;
import java.math.BigDecimal;
import java.time.LocalDate;

public record AtivoEspacialResponse(
        Long id,
        String nome,
        TipoAtivoEnum tipoAtivo,
        String agenciaResponsavel,
        LocalDate dataLancamento,
        BigDecimal massaKg,
        String especificacoesTecnicas,
        Boolean operacional
) {}
