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

        }

    // Ajustado para refletir o novo AtivoEspacialRequest
            AtivoEspacialResponse hub = ativoEspacialService.criar(new AtivoEspacialRequest(
                    "OrbitLink Hub-01",
                    TipoAtivoEnum.SATELITE,
                    "Agência Brasileira de Observação Orbital"
            ));

            // Ajustado para refletir o novo AtivoEspacialRequest
            AtivoEspacialResponse scout = ativoEspacialService.criar(new AtivoEspacialRequest(
                    "Scout Lunar-7",
                    TipoAtivoEnum.SONDA,
                    "Consórcio Espacial Sul-Americano"
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
                    550, // Confirme se no seu projeto este campo é double ou int
                    "Telemetry health normal"
            ));