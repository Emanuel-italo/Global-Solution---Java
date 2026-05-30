package br.com.orbitlink.space.controller;

import br.com.orbitlink.space.dto.AtivoEspacialRequest;
import br.com.orbitlink.space.dto.AtivoEspacialResponse;
import br.com.orbitlink.space.service.AtivoEspacialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

// Importação estática essencial para o HATEOAS
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
        AtivoEspacialResponse response = service.cadastrar(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();

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

        // Link para o próprio recurso (Self)
        model.add(linkTo(methodOn(AtivoEspacialController.class).buscarPorId(response.getId())).withSelfRel());

        // Link para a listagem (opcional, caso tenha um findAll implementado)
        // model.add(linkTo(methodOn(AtivoEspacialController.class).listarTodos()).withRel("todos-ativos"));

        return model;
    }
}