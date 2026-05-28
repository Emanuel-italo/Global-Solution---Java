package br.com.orbitlink.space.dto;

import br.com.orbitlink.space.enums.TipoAtivoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record AtivoEspacialRequest(
        @NotBlank(message = "O nome do ativo espacial é obrigatório.")
        String nome,

        @NotNull(message = "O tipo do ativo espacial é obrigatório.")
        TipoAtivoEnum tipoAtivo,

        @NotBlank(message = "A agência responsável é obrigatória.")
        String agenciaResponsavel,

        @NotNull(message = "A data de lançamento é obrigatória.")
        LocalDate dataLancamento,

        @NotNull(message = "A massa em kg é obrigatória.")
        @Positive(message = "A massa em kg deve ser maior que zero.")
        BigDecimal massaKg,

        @NotBlank(message = "As especificações técnicas são obrigatórias.")
        String especificacoesTecnicas,

        @NotNull(message = "O status operacional é obrigatório.")
        Boolean operacional
) {}
