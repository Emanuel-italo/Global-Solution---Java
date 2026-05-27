package br.com.orbitlink.space.config;

import br.com.orbitlink.space.dto.*;
import br.com.orbitlink.space.enums.CriticidadeAlertaEnum;
import br.com.orbitlink.space.enums.TipoAlertaEnum;
import br.com.orbitlink.space.enums.TipoAtivoEnum;
import br.com.orbitlink.space.service.AlertaOrbitalService;
import br.com.orbitlink.space.service.AtivoEspacialService;
import br.com.orbitlink.space.service.ManutencaoOrbitalService;
import br.com.orbitlink.space.service.RegistroTelemetriaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DadosIniciaisConfig {

    @Bean
    CommandLineRunner popularBanco(
            AtivoEspacialService ativoEspacialService,
            ManutencaoOrbitalService manutencaoOrbitalService,
            RegistroTelemetriaService registroTelemetriaService,
            AlertaOrbitalService alertaOrbitalService
    ) {
        return args -> {
            if (!ativoEspacialService.listarTodos().isEmpty()) {
                return;
            }

            AtivoEspacialResponse hub = ativoEspacialService.criar(new AtivoEspacialRequest(
                    "OrbitLink Hub-01",
                    TipoAtivoEnum.SATELITE,
                    "Agência Brasileira de Observação Orbital",
                    LocalDate.of(2024, 10, 12),
                    new BigDecimal("812.45"),
                    "Nó satelital para coleta e distribuição de dados de clima, conectividade e observação terrestre.",
                    true
            ));

            AtivoEspacialResponse scout = ativoEspacialService.criar(new AtivoEspacialRequest(
                    "Scout Lunar-7",
                    TipoAtivoEnum.SONDA,
                    "Consórcio Espacial Sul-Americano",
                    LocalDate.of(2025, 2, 4),
                    new BigDecimal("230.10"),
                    "Sonda destinada ao monitoramento de rotas orbitais e validação de sinais em baixa latência.",
                    true
            ));

            manutencaoOrbitalService.criar(new ManutencaoOrbitalRequest(
                    hub.id(),
                    LocalDateTime.now().minusDays(7),
                    "Correção de alinhamento solar e revisão de painéis de energia.",
                    new BigDecimal("18500.00")
            ));

            registroTelemetriaService.criar(new RegistroTelemetriaRequest(
                    hub.id(),
                    LocalDateTime.now().minusHours(4),
                    "Céu limpo com baixa interferência eletromagnética",
                    "Sinal estável e em alta disponibilidade",
                    -23.550520,
                    -46.633308,
                    550,
                    "Telemetry health normal"
            ));

            alertaOrbitalService.criar(new AlertaOrbitalRequest(
                    scout.id(),
                    TipoAlertaEnum.RISCO_COLISAO,
                    CriticidadeAlertaEnum.ALTA,
                    "Possível aproximação com microdetritos na órbita de transferência.",
                    LocalDateTime.now().minusMinutes(35),
                    false
            ));
        };
    }
}
