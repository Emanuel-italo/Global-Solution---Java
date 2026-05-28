package br.com.orbitlink.space.dto;

import java.time.LocalDateTime;

public record RegistroTelemetriaResponse(
        Long id,
        Long ativoId,
        String ativoNome,
        LocalDateTime dataRegistro,
        String clima,
        String sinal,
        Double latitude,
        Double longitude,
        Integer distanciaOrbitalKm,
        String observacaoGps
) {}
