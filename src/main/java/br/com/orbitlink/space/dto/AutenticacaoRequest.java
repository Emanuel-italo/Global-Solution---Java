package br.com.orbitlink.space.dto;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoRequest(
        @NotBlank String login,
        @NotBlank String senha
) {
}