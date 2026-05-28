package br.com.orbitlink.space.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

public record RegistroTelemetriaRequest(
        @NotNull(message = "O ID do ativo espacial é obrigatório.")
        Long ativoId,

        @NotNull(message = "A data do registro é obrigatória.")
        LocalDateTime dataRegistro,

        @NotBlank(message = "A informação de clima é obrigatória.")
        String clima,

        @NotBlank(message = "A informação de sinal é obrigatória.")
        String sinal,

        @NotNull(message = "A latitude é obrigatória.")
        Double latitude,

        @NotNull(message = "A longitude é obrigatória.")
        Double longitude,

        @PositiveOrZero(message = "A observação GPS não pode ser negativa.")
        Integer distanciaOrbitalKm,

        String observacaoGps
) {}
