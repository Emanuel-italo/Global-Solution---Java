package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.AlertaOrbitalRequest;
import br.com.orbitlink.space.dto.AlertaOrbitalResponse;
import br.com.orbitlink.space.entity.AlertaOrbital;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import br.com.orbitlink.space.repository.AlertaOrbitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertaOrbitalService {

    private final AlertaOrbitalRepository alertaOrbitalRepository;
    private final AtivoEspacialService ativoEspacialService;

    public AlertaOrbitalService(AlertaOrbitalRepository alertaOrbitalRepository, AtivoEspacialService ativoEspacialService) {
        this.alertaOrbitalRepository = alertaOrbitalRepository;
        this.ativoEspacialService = ativoEspacialService;


    }
}