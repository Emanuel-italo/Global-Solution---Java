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
        

        entidade.setNomeProprietario(request.agenciaResponsavel()); 
        entidade.setNumeroDeSerie("SN-" + System.currentTimeMillis()); 
        

        entidade.setNome(request.nome());

        entidade.setTipo(request.tipoAtivo());
        


        AtivoEspacial salvo = repository.save(entidade);
        return toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public AtivoEspacialResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadeOuFalhar(id));
    }

    @Transactional(readOnly = true)
    public List<AtivoEspacialResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AtivoEspacialResponse atualizar(Long id, AtivoEspacialRequest request) {
        AtivoEspacial entidade = buscarEntidadeOuFalhar(id);
        
        entidade.setNome(request.nome());
        entidade.setTipo(request.tipoAtivo());

        entidade.setNomeProprietario(request.agenciaResponsavel());

        return toResponse(repository.save(entidade));
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
                entidade.getTipo(),
                entidade.getNomeProprietario(),
                null, 
                null, 
                null, 
                true  
        );
    }
}