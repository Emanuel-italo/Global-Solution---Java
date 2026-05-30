package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.ManutencaoOrbitalRequest;
import br.com.orbitlink.space.dto.ManutencaoOrbitalResponse;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.entity.ManutencaoOrbital;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import br.com.orbitlink.space.repository.ManutencaoOrbitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManutencaoOrbitalService {

    private final ManutencaoOrbitalRepository manutencaoOrbitalRepository;
    private final AtivoEspacialService ativoEspacialService;

    public ManutencaoOrbitalService(ManutencaoOrbitalRepository manutencaoOrbitalRepository, AtivoEspacialService ativoEspacialService) {
        this.manutencaoOrbitalRepository = manutencaoOrbitalRepository;
        this.ativoEspacialService = ativoEspacialService;
    }

        @Transactional
    public ManutencaoOrbitalResponse criar(ManutencaoOrbitalRequest request) {
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        ManutencaoOrbital entidade = new ManutencaoOrbital();
        entidade.setAtivoEspacial(ativo);
        entidade.setDataManutencao(request.dataManutencao());
        entidade.setDescricao(request.descricao());
        entidade.setCustoEstimado(request.custoEstimado());

        return toResponse(manutencaoOrbitalRepository.save(entidade));
    }

        @Transactional(readOnly = true)
    public ManutencaoOrbitalResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadeOuFalhar(id));
    }

        @Transactional(readOnly = true)
    public List<ManutencaoOrbitalResponse> listarTodos() {
        return manutencaoOrbitalRepository.buscarTodosComAtivo().stream().map(this::toResponse).toList();
    }

        @Transactional(readOnly = true)
    public List<ManutencaoOrbitalResponse> listarPorAtivoId(Long ativoId) {
        return manutencaoOrbitalRepository.buscarPorAtivoId(ativoId).stream().map(this::toResponse).toList();
    }

        @Transactional
    public ManutencaoOrbitalResponse atualizar(Long id, ManutencaoOrbitalRequest request) {
        ManutencaoOrbital entidade = buscarEntidadeOuFalhar(id);
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        entidade.setAtivoEspacial(ativo);
        entidade.setDataManutencao(request.dataManutencao());
        entidade.setDescricao(request.descricao());
        entidade.setCustoEstimado(request.custoEstimado());

        return toResponse(manutencaoOrbitalRepository.save(entidade));
    }

}