package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.ManutencaoOrbitalRequest;
import br.com.orbitlink.space.dto.ManutencaoOrbitalResponse;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.entity.ManutencaoOrbital;
import br.com.orbitlink.space.repository.ManutencaoOrbitalDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManutencaoOrbitalService {

    private final ManutencaoOrbitalDao manutencaoOrbitalDao;
    private final AtivoEspacialService ativoEspacialService;

    public ManutencaoOrbitalService(ManutencaoOrbitalDao manutencaoOrbitalDao, AtivoEspacialService ativoEspacialService) {
        this.manutencaoOrbitalDao = manutencaoOrbitalDao;
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

        return toResponse(manutencaoOrbitalDao.salvar(entidade));
    }

    @Transactional(readOnly = true)
    public ManutencaoOrbitalResponse buscarPorId(Long id) {
        return toResponse(manutencaoOrbitalDao.buscarPorIdOuFalhar(id));
    }

    @Transactional(readOnly = true)
    public List<ManutencaoOrbitalResponse> listarTodos() {
        return manutencaoOrbitalDao.listarTodos().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ManutencaoOrbitalResponse> listarPorAtivoId(Long ativoId) {
        return manutencaoOrbitalDao.listarPorAtivoId(ativoId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public ManutencaoOrbitalResponse atualizar(Long id, ManutencaoOrbitalRequest request) {
        ManutencaoOrbital entidade = manutencaoOrbitalDao.buscarPorIdOuFalhar(id);
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        entidade.setAtivoEspacial(ativo);
        entidade.setDataManutencao(request.dataManutencao());
        entidade.setDescricao(request.descricao());
        entidade.setCustoEstimado(request.custoEstimado());

        return toResponse(manutencaoOrbitalDao.salvar(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        manutencaoOrbitalDao.deletar(id);
    }

    private ManutencaoOrbitalResponse toResponse(ManutencaoOrbital entidade) {
        return new ManutencaoOrbitalResponse(
                entidade.getId(),
                entidade.getAtivoEspacial().getId(),
                entidade.getAtivoEspacial().getNome(),
                entidade.getDataManutencao(),
                entidade.getDescricao(),
                entidade.getCustoEstimado()
        );
    }
}
