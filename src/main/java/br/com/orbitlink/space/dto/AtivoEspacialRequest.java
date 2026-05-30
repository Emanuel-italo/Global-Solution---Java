package br.com.orbitlink.space.dto;

import br.com.orbitlink.space.enums.TipoAtivoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtivoEspacialRequest(
        @NotBlank(message = "O nome não pode estar em branco")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "O tipo do ativo é obrigatório")
        TipoAtivoEnum tipoAtivo,

        @NotBlank(message = "O nome do proprietário é obrigatório")
        @Size(max = 100, message = "O nome do proprietário deve ter no máximo 100 caracteres")
        String agenciaResponsavel
) {
}