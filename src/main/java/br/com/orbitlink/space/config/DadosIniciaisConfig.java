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