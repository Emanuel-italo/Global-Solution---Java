package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.AtivoEspacialRequest;
import br.com.orbitlink.space.dto.AtivoEspacialResponse;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import br.com.orbitlink.space.repository.AtivoEspacialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AtivoEspacialService {

    private final AtivoEspacialRepository repository;

    public AtivoEspacialService(AtivoEspacialRepository repository) {
        this.repository = repository;
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

        // Agora usa o JpaRepository
        AtivoEspacial salvo = repository.save(entidade);
        return toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public AtivoEspacialResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadeOuFalhar(id));
    }

    @Transactional(readOnly = true)
    public List<AtivoEspacialResponse> listarTodos() {
        return repository.findAll() // Método nativo do JpaRepository
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

        return toResponse(repository.save(entidade)); // save funciona tanto para insert quanto para update
    }

    @Transactional
    public void descomissionar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoLocalizadaException("Ativo espacial não encontrado com id " + id);
        }
        repository.descomissionarAtivo(id);
    }

    @Transactional(readOnly = true)
    public AtivoEspacial buscarEntidadeOuFalhar(Long id) {
        return repository.findById(id)
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