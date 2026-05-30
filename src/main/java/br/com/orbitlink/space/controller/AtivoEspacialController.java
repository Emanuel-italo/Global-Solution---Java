package br.com.orbitlink.space.controller;

import br.com.orbitlink.space.dto.AtivoEspacialRequest;
import br.com.orbitlink.space.dto.AtivoEspacialResponse;
import br.com.orbitlink.space.service.AtivoEspacialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

// Importações estáticas para o HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/ativos")
@Tag(name = "Ativos Espaciais", description = "Endpoints para gerenciamento de ativos na economia espacial")
public class AtivoEspacialController {

    @Autowired
    private AtivoEspacialService service;

    @PostMapping
    @Operation(summary = "Cadastrar um novo Ativo Espacial", description = "Persiste um ativo no banco de dados e retorna o recurso criado com HATEOAS.")
    public ResponseEntity<EntityModel<AtivoEspacialResponse>> cadastrar(@RequestBody @Valid AtivoEspacialRequest request) {
        // Correção: alterado de "cadastrar" para "criar" para corresponder ao Service
        AtivoEspacialResponse response = service.criar(request);

        // Correção: alterado de "response.getId()" para "response.id()" por ser um Record
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(adicionarLinks(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Ativo por ID", description = "Retorna os detalhes de um ativo específico.")
    public ResponseEntity<EntityModel<AtivoEspacialResponse>> buscarPorId(@PathVariable Long id) {
        AtivoEspacialResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(adicionarLinks(response));
    }

    // Método utilitário para adicionar os Links do HATEOAS
    private EntityModel<AtivoEspacialResponse> adicionarLinks(AtivoEspacialResponse response) {
        EntityModel<AtivoEspacialResponse> model = EntityModel.of(response);

        // Correção: alterado de "response.getId()" para "response.id()" por ser um Record
        model.add(linkTo(methodOn(AtivoEspacialController.class).buscarPorId(response.id())).withSelfRel());

        return model;
    }
}