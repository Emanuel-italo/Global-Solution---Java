package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.AtivoEspacialRequest;
import br.com.orbitlink.space.dto.AtivoEspacialResponse;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import br.com.orbitlink.space.repository.AtivoEspacialDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AtivoEspacialService {

    private final AtivoEspacialDao ativoEspacialDao;

    public AtivoEspacialService(AtivoEspacialDao ativoEspacialDao) {
        this.ativoEspacialDao = ativoEspacialDao;
    }

    @Transactional
    public AtivoEspacialResponse criar(AtivoEspacialRequest request) {
        AtivoEspacial entidade = new AtivoEspacial();
        entidade.setNome(request.nome());
        entidade.setTipoAtivo(request.tipoAtivo());
        entidade.setAgenciaResponsavel(request.agenciaResponsavel());
        entidade.setDataLancamento(request.dataLancamento());
        entidade.setMassaKg(request.massaKg());
        entidade.setEspecificacoesTecnicas(request.especificacoesTecnicas());
        entidade.setOperacional(request.operacional());

        AtivoEspacial salvo = ativoEspacialDao.salvar(entidade);
        return toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public AtivoEspacialResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadeOuFalhar(id));
    }

    @Transactional(readOnly = true)
    public List<AtivoEspacialResponse> listarTodos() {
        return ativoEspacialDao.listarTodos()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AtivoEspacialResponse atualizar(Long id, AtivoEspacialRequest request) {
        AtivoEspacial entidade = buscarEntidadeOuFalhar(id);
        entidade.setNome(request.nome());
        entidade.setTipoAtivo(request.tipoAtivo());
        entidade.setAgenciaResponsavel(request.agenciaResponsavel());
        entidade.setDataLancamento(request.dataLancamento());
        entidade.setMassaKg(request.massaKg());
        entidade.setEspecificacoesTecnicas(request.especificacoesTecnicas());
        entidade.setOperacional(request.operacional());

        return toResponse(ativoEspacialDao.atualizar(entidade));
    }

    @Transactional
    public void descomissionar(Long id) {
        if (!ativoEspacialDao.existePorId(id)) {
            throw new EntidadeNaoLocalizadaException("Ativo espacial não encontrado com id " + id);
        }
        ativoEspacialDao.descomissionar(id);
    }

    @Transactional(readOnly = true)
    public AtivoEspacial buscarEntidadeOuFalhar(Long id) {
        return ativoEspacialDao.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoLocalizadaException("Ativo espacial não encontrado com id " + id));
    }

    private AtivoEspacialResponse toResponse(AtivoEspacial entidade) {
        return new AtivoEspacialResponse(
                entidade.getId(),
                entidade.getNome(),
                entidade.getTipoAtivo(),
                entidade.getAgenciaResponsavel(),
                entidade.getDataLancamento(),
                entidade.getMassaKg(),
                entidade.getEspecificacoesTecnicas(),
                entidade.getOperacional()
        );
    }
}
